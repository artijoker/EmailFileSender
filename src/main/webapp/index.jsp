<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
</head>
<body>
<div class="m-3 d-flex justify-content-center align-items-center flex-column">
    <h1>Отправить электронное письмо</h1>
    <form class="w-50 mt-4 " action="send-message" method="post" enctype='multipart/form-data'>
        <div class="form-group">
            <label for="nameId">Получатель*</label>
            <input type="text" id="nameId" class="form-control" name="recipient" required>
        </div>
        <div class="form-group mt-2">
            <label for="subjectId">Тема</label>
            <input type="text" id="subjectId" class="form-control" name="subject">
        </div>
        <div class="form-group">
            <label for="bodyId">Текст</label>
            <textarea class="form-control" id="bodyId" rows="5" cols="30" name="body"></textarea>
        </div>
        <div class="form-group mt-2">
            <label for="fileId">Файл</label>
            <input type="file" id="fileId" class="form-control" name="file1">
        </div>

        <div class="text-center mt-4" >
            <button type="submit" class="btn btn-primary mt-4 w-50">Отправить</button>
        </div>

    </form>
</div>

</body>
</html>
<%--style="resize: none"--%>