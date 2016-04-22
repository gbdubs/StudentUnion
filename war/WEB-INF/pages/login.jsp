<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor Login</title>
    </head>
    
    <body>
        <style>
            body {
                font-family: sans-serif;
            }
            .login-wrapper {
                width: 500px;
                max-width: Calc(100vw - 10px);
                padding: Calc(50vh - 250px) 0 40px Calc(50vw - 250px);
                z-index: 2;
                position: absolute;
            }
            .footer{
                position: fixed;
                bottom: 0;
                padding: 10px;
                margin: 0;
                left: 0;
                z-index: 2;
            }
            .centered{
                text-align: center;
            }
            .scc {
                position: fixed;
                z-index: 1;
                margin: Calc(50vh - 450px) Calc(50vw - 600px);
            }
            .login-btn{
                background: #00DC11;
                padding: 10px;
                color: white;
                border: 2px solid #32B73C;
                text-decoration: none;
                border-radius: 3px;
                box-shadow: 0px 2px 10px 1px rgba(0,0,0,.2);
            }
            .login-btn:hover{
                box-shadow: 0px 2px 10px 1px rgba(0,0,0,.4);
            }
            
            .footer,
            .login-wrapper > div {
                background: rgba(255, 255, 255, .95);
                padding: 5px 15px;
                border-radius: 5px;
                box-shadow: 0px 2px 10px 3px rgba(0,0,0,.5);
                border: 1px solid gray;
            }
            
        </style>
        <img class="scc" src="/static/img/scc.png"/>
        <div class="login-wrapper">
            <div>
                <h1 class="centered">Student Union Website Editor</h1>
                <p class="centered">The Student Union Website Editor works via your Brandeis Login.</p>

                <p class="centered"><a href="${loginUrl}" class="login-btn">Login Here</a></p>

                <p>You will only be able to login if you have been made an administrator or owner by a current site owner.</p>
                <p>The Current Site Owners are:</p>
                <ul>
                <c:forEach items="${owners}" var="owner">
                    <li>
                        <b>${owner.nickname}</b> - 
                        <a href="mailto:${owner.email}">${owner.email}</a>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
        
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>