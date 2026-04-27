<%-- 学生情報登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form method="get" action="StudentUpdateExecute.action">
			<div class="mx-3 my-4">
				<div class="mb-3">
					<label class="form-text-lavel" for="entYear">入学年度</label>
					<input class="form-control-plaintext" type="text" name="ent_year" id="entYear"
						value="${student.getEntYear()}" readonly />
				</div>
				<div class="mb-3">
					<label class="form-text-lavel" for="studentNo">学生番号</label>
					<input class="form-control-plaintext ml-3" type="text" name="no" id="studentNo"
						value="${student.getNo()}" readonly />
				</div>
				<div class="mb-3">
					<label class="form-text-label" for="studentName">氏名</label>
					<input class="form-control" name="name" type="text" id="studentName"
						placeholder="氏名を入力してください" value="${student.getName()}"
						maxlength="30" required />
				</div>
				<div class="mb-3">
					<label class="form-text-label" >クラス</label>
					<select class="form-select" name="class_num">
						<c:forEach var="num" items="${class_num_set}">
							<option value="${num}" <c:if test="${num == student.getClassNum()}">selected</c:if>>${num}</option>
						</c:forEach>
					</select>
				</div>
				<div class="mb-3">
					<label class="form-check-label" for="check">在学中</label>
					<input class="form-check-input" id="check" type="checkbox" name="is_attend" value="true" <c:if test="${student.isAttend()}">checked</c:if>>
				</div>
				<div class="mb-3 container">
					<div class="row">
						<input class="col btn btn-primary" type="submit" id="filter-button" value="変更" style="max-width:60px">

					<div class="col d-flex justify-content-end">
						<a href="StudentDelete.action?no=${student.getNo()}" class="btn btn-danger" role="button" style="max-width:60px">
							削除
						</a>
					</div>
					</div>
				</div>
				<div><a href="StudentList.action">戻る</a></div>
			</div>
			</form>
		</section>
	</c:param>
</c:import>