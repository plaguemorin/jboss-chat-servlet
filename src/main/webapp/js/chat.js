/**
 * Created by IntelliJ IDEA.
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
var loggedIn = false;
var userId = "";
var roomId = "";

function startChat(lRoomId) {
    $(".chat").fadeIn();
    loggedIn = true;
    roomId = lRoomId;

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

    userId = $("#login-user").val();

    // Do Login
    $(".chat").fadeIn();
}

function createRoom() {
    var newRoomId = prompt("Room Name");
    if (newRoomId == "" || newRoomId == undefined) {
        return;
    }

    $.ajax({
        url: "chatServices/room/" + newRoomId + "/",
        type: "POST",
        success: function() {
            alert("Room has been created");
        }

    });
}

function joinRoom() {
    if (roomId != "") {
        // UNREGISTER
        alert("Not supported yet; please refresh the browser");
        return;
    }

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
        }
    });
}

$(document).ready(function() {
    $("#login-login").click(doLogin);

    $(".sendMessage").click(sendMessage);
    $(".createRoom").click(createRoom);
    $(".joinRoom").click(joinRoom);
});
