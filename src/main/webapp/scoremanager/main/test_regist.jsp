<%-- 学生一覧JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<tr>
				<form method="get" action="TestRegist.action">
					<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
						<div class="col-4">
							<th>入学年度</th>
							<select class="form-select" id="student-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set }">
									<%-- 現在のyearと選択されていたf1が一致した場合selectedを追記 --%>
									<option value="${year}" <c:if test="${year == f1}"> selected </c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-4">
							<th>クラス</th>
							<select class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<%-- 現在のyearと選択されていたf2が一致した場合selectedを追記 --%>
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-4">
							<th>科目</th>
							<select class="form-select" id="student-f3-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject.cd" items="${subject_subject.cd_set}">
									<%-- 現在のyearと選択されていたf3が一致した場合selectedを追記 --%>
									<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-4">
							<th>回数</th>
							<select class="form-select" id="student-f4-select" name="f4">
									<%-- 現在のyearと選択されていたf4が一致した場合selectedを追記 --%>
									<option value="${num}" <c:if test="${num == f4}">selected</c:if>>1</option>
									<option value="${num}" <c:if test="${num == f4}">selected</c:if>>2</option>
							</select>
						</div>
						<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
					</div>
				</form>
			</tr>
		</section>
	</c:param>
</c:import>