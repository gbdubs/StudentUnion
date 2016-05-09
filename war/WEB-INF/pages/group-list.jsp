<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">

		<h1 class="light center-align">List of Groups</h1>

		<br>

		<p>
			The student Union has a number of branches, working groups and committees.
			Each group has a list of members, conveniently organizing their contact information in a single place.
			For more information about a group, reach out to its leaders. 
		</p>
	
		<br>
		<ul class="no-bullet-list">
			<c:forEach items="${groups}" var="group">
				<li>
					<h5>
						<b>${group.name}</b>
						 - 
						<a href="${group.pageUrl}">
							Group Page
						</a>
						-
						<a href="${group.pageUrl}/members">
							Members
						</a>
					</h5>
					Description: ${group.description}
					<br>
					<br>
				</li>
			</c:forEach>
		</ul>

	</jsp:attribute>
</t:page>
