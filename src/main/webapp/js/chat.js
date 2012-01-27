/**
 * Created by IntelliJ IDEA.
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
var loggedIn = false;
var userId = "1";
var roomId = "";

function startChat(userId, roomId) {
    $(".chat").fadeIn();
    loggedIn = true;

    update();
}

function update() {
    $.ajax({
        url: "AsynchronousServlet?userId=" + userId + "&roomId=" + roomId,
        type: "GET",
        success: function(data) {
            console.log("new data", data);
            $(".messages").append("<p>" + $(data).find("message").text() + "</p>");
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
    $(".messageToSend").prop('disabled', true);

    $.ajax({
        url: "chatServices/room/1",
        type: "PUT",
        data: {message: message},
        success: function() {
            $(".messageToSend").prop('disabled', false);
            $(".messageToSend").val("").focus();
        }
    });

    return false;
}

function doLogin() {
    $(".login").fadeOut();

    var createRoomId = "1";
    $.ajax({
        url: "chatServices/room/" + createRoomId + "/",
        type: "POST",
        success: function() {

            $.ajax({
                url: "chatServices/room/" + createRoomId + "/membership/?userId=" + userId,
                type: "POST",
                success: function(data) {
                    console.log(data);
                    roomId = createRoomId;
                    startChat(roomId, userId);
                }
            });

        }
    });
}

$(document).ready(function() {
    $(".sendMessage").click(sendMessage);
    $("#login-login").click(doLogin);
});
