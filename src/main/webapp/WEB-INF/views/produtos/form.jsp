<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livro de java, android, iphone, ruby, php e muito mais - casa do código</title>
</head>
<body>
	<!-- no lugar de usar action="/casadocodigo/produtos" usamos o spring que já conhece a controle para fazer isso, nisso usamos as primeiras letras inciais da controle que é PC de ProdutoController e depois o metodo gravar, ficando ('PC#gravar')-->
	<!-- commandName="produto" para espeficicar apenas o atributos sem o nome, exemplo: <form:errors path="produto.titulo"/> com isso eu uso assim: <form:errors path="titulo"/> -->
	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="POST" commandName="produto">
<%-- 	<form action="/casadocodigo/produtos" method="POST"> --%>
		<div>
			<label>Titulo</label>
		<!-- 'form:input' Para quando tiver algum erro e a pagina fazer refresh, não perder os campos preenchidos no formulario -->
			<form:input path="titulo"/>
			<form:errors path="titulo"/>
		</div>
		<div>
			<label>Descricao</label>
			<form:textarea rows="10" cols="20" path="descricao"/>
			<form:errors path="descricao"/>
		</div>
		<div>
			<label>Páginas</label>
			<form:input path="paginas"/>
			<form:errors path="paginas"/>
		</div>
		<div>
			<label>Data de lançamento</label>
			<form:input path="dataLancamento"/>
			<form:errors path="dataLancamento"/>
		</div>
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label>
				<form:input path="precos[${status.index}].valor"/>
				<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}"/>
			</div>
		</c:forEach>
		
		<button type="submit">Cadastrar</button>
	</form:form>
</body>
</html>