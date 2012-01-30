/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:17 PM
 */

var chatUserBackend = {
    data: {
        userKey: 0,
        ready: false
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

        return (userData != null);
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
                onNewMessageCallback: onNewMessageCallback,

                postMessage: function(message) {
                    return chatUserBackend.sendMessage(this.roomId, message);
                },

                update: function() {
                    console.log("Update on room " + this.roomId);

                    $.ajax({
                        url: "AsynchronousServlet?userId=" + chatUserBackend.data.userKey + "&roomId=" + this.roomId,
                        type: "GET",
                        success: function(data, status, xhr) {
                            this.onNewMessageCallback(data);
                            setTimeout(ret.update, 100);
                        },
                        error: console.log
                    });
                }
            }
        }

        return ret;
    }

};


var loggedIn = false;
var userId = "";
var roomId = "";

function startChat(lRoomId) {
    $("#chatContainer").fadeIn();
    loggedIn = true;
    roomId = lRoomId;

    $(".roomName").text("Room: " + roomId);
    update();
}

function update() {
    $.ajax({
        url: "AsynchronousServlet?userId=" + userId + "&roomId=" + roomId,
        type: "GET",
        success: function(data) {
            console.log("new data", data);
            $(".messages").append("<p class='singleMessage'><span class='userName'>"
                + "<span class='date'>"
                + (new Date(parseInt($(data).find("date").text()))).toTimeString()
                + "</span>"
                + $(data).find("userId").text()
                + "</span> <span class='message'>"
                + $(data).find("message").text()
                + "</span></p>");

            $("body").animate({ scrollTop: $(document).height() }, "slow");
            $(".messageToSend").focus();
            setTimeout(update, 100);
        },

        error: function() {
            loggedIn = false;
            $(".chat").fadeOut();
            $(".login").fadeIn();
        }

    });
}

function sendMessage() {
    var message = $(".messageToSend").val();

    if (message != "") {
        $(".messageToSend").prop('disabled', true);
        $.ajax({
            url: "chatServices/room/" + roomId,
            type: "PUT",
            data: {message: message, userId: userId},
            success: function() {
                $(".messageToSend").prop('disabled', false);
                $(".messageToSend").val("").focus();
            }
        });
    }

    return false;
}

function doLogin() {
    $(".login").fadeOut();
    userId = $("#login-user").val();

    $(".roomBtns").fadeIn();
}

function createRoom() {
    var newRoomId = prompt("Room Name");
    if (newRoomId == "" || newRoomId == undefined) {
        return;
    }

    $(".roomBtns").fadeOut();
    $.ajax({
        url: "chatServices/room/" + newRoomId + "/",
        type: "POST",
        success: function() {
            alert("Room has been created");
            $(".roomBtns").fadeIn();
        }
    });
}

function joinRoom() {
    if (roomId != "") {
        // UNREGISTER
        alert("Not supported yet; please refresh the browser");
        return;
    }

    $(".roomBtns").fadeOut();

    var newRoomId = prompt("Join room:");
    if (newRoomId == "" || newRoomId == undefined) {
        roomId = "";
        return;
    }

    $.ajax({
        url: "chatServices/room/" + newRoomId + "/membership/?userId=" + userId,
        type: "POST",
        success: function() {
            startChat(newRoomId);
        },
        error: function(data) {
            $(".roomBtns").fadeIn();
            alert(data.statusText + "\nAre you sure the room exists");
        }
    });
}

$(document).ready(function() {
    $("#login-login").click(doLogin);

    $("#submitForm input[type='submit']").click(sendMessage);
    $(".createRoom").click(createRoom);
    $(".joinRoom").click(joinRoom);

    $("#form-login").submit(function() {
        doLogin();
        return false;
    });
});
