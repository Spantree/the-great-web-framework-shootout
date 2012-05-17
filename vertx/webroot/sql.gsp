<!doctype html>
<html>
	<head>
		<title>Hello World</title>
	</head>
	<body>
		<table>
			<% hellos.each { hello -> %>
			<tr>
			  <td>${hello.id}</td>
			  <td>${hello.data}</td>
			</tr>
			<% } %>
		</table>
	</body>
</html>
