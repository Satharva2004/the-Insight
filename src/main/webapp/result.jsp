<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>
        <link rel="stylesheet" type="text/css" href="style2.css">
    <head>
        <meta charset="UTF-8">
        <title>Smmry Result</title>
    </head>
    
    <body>
        <div class="ripple-background">
            <div class="circle xxlarge shade1"></div>
            <div class="circle xlarge shade2"></div>
            <div class="circle large shade3"></div>
            <div class="circle medium shade4"></div>
            <div class="circle small shade5"></div>
        </div>
        <div class="content">
                        <c:if test="${not empty summary}">
                            <p>${summary}</p>
                        </c:if>
                    </div>
        
    </body>

    </html>