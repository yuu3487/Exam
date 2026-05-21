<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">

<c:param name="title">得点管理システム</c:param>

<c:param name="content">

<section class="me-4">

    <!-- タイトル -->
    <h2 class="h3 mb-4 bg-secondary bg-opacity-10 py-3 px-4">
        成績管理
    </h2>

    <!-- ========================= -->
    <!-- 検索フォーム -->
    <form method="get" action="TestRegist.action">
    <div class="row border rounded p-3 mb-4">

        <div class="col-3">
            <label>入学年度</label>
            <select class="form-select" name="f1">
                <option value="0">--------</option>
                <c:forEach var="year" items="${ent_year_set}">
                    <option value="${year}"
                        <c:if test="${year == f1}">selected</c:if>>
                        ${year}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-3">
            <label>クラス</label>
            <select class="form-select" name="f2">
                <option value="0">--------</option>
                <c:forEach var="num" items="${class_num_set}">
                    <option value="${num}"
                        <c:if test="${num == f2}">selected</c:if>>
                        ${num}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-4">
            <label>科目</label>
            <select class="form-select" name="f3">
                <option value="">--------</option>
                <c:forEach var="subject" items="${subjectList}">
                    <option value="${subject.cd}"
                        <c:if test="${subject.cd == f3}">selected</c:if>>
                        ${subject.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-2">
            <label>回数</label>
            <select class="form-select" name="f4">
                <option value="">--</option>
                <option value="1" <c:if test="${f4 == '1'}">selected</c:if>>1</option>
                <option value="2" <c:if test="${f4 == '2'}">selected</c:if>>2</option>
            </select>
        </div>

        <div class="col-2 d-flex align-items-end">
            <button class="btn btn-secondary w-100">検索</button>
        </div>

    </div>
    </form>

    <!-- ========================= -->
    <!-- 結果表示 -->
    <!-- ========================= -->
    <c:if test="${not empty tests}">
		<c:forEach var="subject" items="${subjectList}">
                        <c:if test="${subject.cd == f3}">
                        	<h5 class="mb-3">
            					科目：${subject.name}（${f4}回）
        					</h5>
        				</c:if>
                </c:forEach>
        

        <form method="post" action="TestRegistExecute.action">

		    <input type="hidden" name="f1" value="${f1}">
		    <input type="hidden" name="f2" value="${f2}">
		    <input type="hidden" name="f3" value="${f3}">
		    <input type="hidden" name="f4" value="${f4}">
		
		    <table class="table">
		        <thead class="table-light">
		            <tr>
		                <th>入学年度</th>
		                <th>クラス</th>
		                <th>学生番号</th>
		                <th>氏名</th>
		                <th>点数</th>
		            </tr>
		        </thead>
		
		        <tbody>
		        <c:forEach var="test" items="${tests}">
		            <tr>
		                <td>${test.entYear}</td>
		                <td>${test.classNum}</td>
		                <td>${test.studentNo}</td>
		                <td>${test.name}</td>
						<td>
						    <input type="text"
						        name="point_${test.studentNo}"
						        value="${test.point}"
						        class="form-control" style="width:150px;">
						
						    <!-- ✅ エラー表示 -->
						    <c:if test="${errors[test.studentNo] != null}">
						        <div style="color:red; font-size:small;">
						            ${errors[test.studentNo]}
						        </div>
						    </c:if>
						</td>
		            </tr>
		        </c:forEach>
		        </tbody>
		    </table>
		
		    <button class="btn btn-secondary mt-3">
		        登録して終了
		    </button>
		
		</form>

    </c:if>

</section>

</c:param>
</c:import>
