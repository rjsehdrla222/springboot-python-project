<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <jsp:scriptlet>

        pageContext.setAttribute("cr", "\r");
        pageContext.setAttribute("lf", "\n");
        pageContext.setAttribute("crlf", "\r\n");

    </jsp:scriptlet>

</head>
<body>
<h2>hello?</h2>
<c:forEach items="${comment}" var="comment">
    이건? ${comment.id} <p></p>
    작성자: ${comment.name} <p></p>
    제목: ${comment.title} <p></p>
    조회수: ${comment.view_cnt} <p></p>
    내용: <pre>${comment.comment} </pre> <p></p>
    작성일: ${comment.create_date} <p></p>
    로그인 아이디: ${comment.login_id} <p></p>
    <c:if test="${member == comment.login_id}">
        <div id="row1" style="float:left;">
            <form action="/commentUpdate/${comment.id}" method="get">
                <button type="submit">수정</button>
            </form>
        </div>
        <div id="row2" style="float:left;">
            <form action="/commentDelete/${comment.id}" method="post">
                <button type="submit">삭제</button>
            </form>
        </div>
    </c:if>
</c:forEach>
</body>
</html>