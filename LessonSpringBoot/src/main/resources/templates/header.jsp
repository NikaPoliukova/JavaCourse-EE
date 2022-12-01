<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
    body {
        font-family: Arial, Helvetica, sans-serif;
    }

    .navbar {
        width: 100%;
        background-color: #555;
        overflow: auto;
    }

    .navbar a {
        float: left;
        padding: 12px;
        color: white;
        text-decoration: none;
        font-size: 17px;
    }

    .navbar a:hover {
        background-color: #000;
    }

    .active {
        background-color: #04AA6D;
    }

    @media screen and (max-width: 500px) {
        .navbar a {
            float: none;
            display: block;
        }
    }
</style>
<body>
<h2>My social network</h2>
<div class="navbar">
    <a class="active" href="#"><i class="fa fa-fw fa-user"></i> My page</a>
    <a class="active" href="users"><i class="fa fa-fw fa-address-book"></i> Users </a>
    <a class="active" href="friends"><i class="fa fa-handshake-o"></i> Friends</a>
    <a class="active" href="incoming_and_added_friend_requests"><i class="fa fa-fw fa-arrow-down"></i> Incoming friend
        requests</a>
    <a class="active" href="outgoing_friend_requests"><i class="fa fa-fw fa-arrow-up"></i> Outgoing friend requests</a>
</div>
</body>
</html>