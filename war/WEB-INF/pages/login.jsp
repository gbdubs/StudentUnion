<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div class="section">
			
        
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
        
			</div>
		</div>
	</jsp:attribute>
</t:page>