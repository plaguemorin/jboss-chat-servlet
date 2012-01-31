/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:17 PM
 */

var chatUserBackend = {
    data: {
        userKey: 0,
        ready: false,
        rooms: []
    },

    init: function() {
    },

    sendMessage: function(roomId, message) {
        $.ajax({
            url: "chatServices/room/" + roomId + "/messages/",
            type: "PUT",
            async: false,
            data: {message: message, userId: this.data.userKey},
            success: function(data) {

            },

            error: function() {

            }
        });
    },

    /**
     * Register a user (this should be the first step)
     *
     * @param nickname String display name
     * @param email String email to login with
     */
    registerUser: function(email, nickname) {
        var userData = null;
        console.log("Registering " + email + " and using nick " + nickname);

        $.ajax({
            url: "chatServices/registration/?userEmail=" + email,
            type: "PUT",
            async: false,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                userData = data;
            },
            error: function() {
                alert("ERROR !");
            }
        });

        console.log("User " + email + " has key " + userData);

        this.data.userKey = userData;
        this.data.ready = true;
        this.changeNickname(nickname);

        return (userData != null) ? this.userInfo(userData) : false;
    },

    logout: function() {
        $.ajax({
            url: "charServices/user/" + this.data.userKey + "/",
            type: "DELETE",
            async: false
        });

        this.data.userKey = "";
        this.data.ready = false;
    },

    listUsers: function(roomId) {
        var userData = null;
        $.ajax({
            url: "chatServices/room/" + roomId + "/membership/",
            type: "GET",
            async: false,
            success: function(data, ts, jxhr) {
                userData = data;
            }
        });

        return userData;
    },

    userInfo: function(userKey) {
        var userData = null;
        $.ajax({
            url: "chatServices/user/" + userKey,
            type: "GET",
            async: false,
            success: function(data, ts, jxhr) {
                userData = data;
            }
        });

        return userData;
    },

    changeNickname: function(newNick) {
        $.ajax({
            url: "chatServices/user/" + this.data.userKey + "/nickname/",
            type: "POST",
            data: {nick: newNick},
            async: false
        });
        return true;
    },

    joinRoom: function(roomId, onNewMessageCallback) {
        var ret = false;

        if (!this.data.ready) {
            return false;
        }

        $.ajax({
            url: "chatServices/room/" + roomId + "/membership/?userId=" + this.data.userKey,
            type: "POST",
            async: false,
            success: function() {
                ret = true;
            },
            error: function(data) {
                alert(data.statusText + "\nAre you sure the room exists?");
            }
        });

        if (ret) {
            ret = {
                roomId: roomId,
                onMessageCallback: onNewMessageCallback,

                sendMessage: function(message) {
                    chatUserBackend.sendMessage(roomId, message);
                },

                listUsers: function() {
                    return chatUserBackend.listUsers(roomId);
                },

                error: function() {

                },

                update: function() {
                    $.ajax({
                        url: "AsynchronousServlet?userId=" + chatUserBackend.data.userKey + "&roomId=" + roomId,
                        type: "GET",
                        success: ret.onMessageCallback,
                        error: ret.error,
                        complete: function() {
                            setTimeout(ret.update, 100);
                        }
                    });
                }
            };

            ret.update();
        }

        return ret;
    }
};

var chatUI = {
    data: {
        room: null,
        templateMessage: null,
        templateUser: null,
        jsAPI: null,
        lastId: 0
    },

    init: function(theRoom) {
        this.data.room = theRoom;

        this.data.jsAPI = $("#chatLineHolder").jScrollPane({
            verticalDragMinHeight: 12,
            verticalDragMaxHeight: 12
        }).data("jsp");

        $("#submitForm").submit(function() {
            theRoom.sendMessage($("#chatText").val());
            $("#chatText").val("").focus();
            return false;
        });

        this.setTitle(this.data.room.roomId);

        var sourceTemplateMessages = $("#template-message").html();
        var sourceTemplateUser = $("#template-user").html();

        this.data.templateMessage = Handlebars.compile(sourceTemplateMessages);
        this.data.templateUser = Handlebars.compile(sourceTemplateUser);
    },

    setTitle: function(title) {
        $("#chatTopBar span.name").text(title);
    },

    setGravatar: function(url) {
        $("#chatTopBar img").attr("src", url);
    },

    updateUserList:function() {
        $("#chatUsers").children().remove();
        var users = chatUserBackend.listUsers(this.data.roomId);

        $(users).each(function(key, value) {
            var html = chatUI.data.templateUser({
                userKey: value.id,
                nickname: value.nickname,
                avatarpath: value.avatarUrl
            });

            $("#chatUsers").append(html);
        });
    },

    addChatLine: function(date, message, userId) {
        var jsAPI = this.data.jsAPI;
        var dateObj = new Date(parseInt(date));
        var userObj = chatUserBackend.userInfo(userId);

        var html = this.data.templateMessage({
            message: message,
            date: dateObj.getHours() + ":" + dateObj.getMinutes(),
            author: userObj.nickname,
            avatarpath: userObj.avatarUrl,
            id: this.data.lastId++
        });

        jsAPI.getContentPane().append(html);
        jsAPI.reinitialise();
        jsAPI.scrollToBottom(true);
    },

    addSystemLine: function(systemMessage) {

    }
};

var room = null;

function updateDisplay(data) {
    var date = $(data).find("date").text();
    var message = $(data).find("message").text();
    var roomid = $(data).find("roomId").text();
    var userid = $(data).find("userId").text();

    if (data.documentElement.tagName == "newRoomMessageNotification") {
        chatUI.addChatLine(date, message, userid);
    } else {
        console.log(data);

        // For now
        chatUI.updateUserList();
    }
}

function doLogin() {
    var email = $("#login-user").val();
    var nick = $("#login-nick").val();

    var selfInfo = chatUserBackend.registerUser(email, nick);

    if (selfInfo !== false) {
        room = chatUserBackend.joinRoom("hybris", updateDisplay);

        chatUI.init(room);
        chatUI.setTitle("Room: hybris");
        chatUI.setGravatar(selfInfo.avatarUrl);
        chatUI.updateUserList();

        $("#login").fadeOut();
        $("#chatContainer").fadeIn();

        $(document).unload(function() {
            chatUserBackend.logout();
        });
    }
}

$(document).ready(function() {
    $("#login-login").click(doLogin);
    
});
