<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績参照
            </h2>

            <!-- ▼ 科目検索フォーム -->
            <div class="border mx-3 mb-3 py-3 px-4 rounded">
                <h5 class="mb-3">科目情報</h5>

                <form method="get" action="TestListSubjectExecute.action">
                    <div class="row align-items-end">

                        <!-- 入学年度 -->
                        <div class="col-3">
                            <label class="form-label">入学年度</label>
                            <select class="form-select" name="ent_year">
                                <option value="">--------</option>
                                <c:forEach var="y" items="${ent_year_set}">
                                    <option value="${y}" <c:if test="${y == ent_year}">selected</c:if>>
                                        ${y}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- クラス -->
                        <div class="col-3">
                            <label class="form-label">クラス</label>
                            <select class="form-select" name="class_num">
                                <option value="">--------</option>
                                <c:forEach var="c" items="${class_num_set}">
                                    <option value="${c}" <c:if test="${c == class_num}">selected</c:if>>
                                        ${c}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 科目 -->
                        <div class="col-3">
                            <label class="form-label">科目</label>
                            <select class="form-select" name="subject_cd">
                                <option value="">--------</option>
                                <c:forEach var="s" items="${subject_set}">
                                    <option value="${s.cd}" <c:if test="${s.cd == subject_cd}">selected</c:if>>
                                        ${s.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 検索ボタン -->
                        <div class="col-3 d-flex justify-content-end">
                            <button class="btn btn-secondary mt-4">検索</button>
                        </div>

                    </div>
                </form>
            </div>

            <!-- ▼ エラー表示（写真と同じ位置） -->
            <c:if test="${not empty message}">
                <div class="alert alert-warning py-2 px-3" style="margin-bottom: 20px;">
                    ${message}
                </div>
            </c:if>

            <!-- ▼ 学生番号検索フォーム -->
            <div class="border mx-3 mb-3 py-3 px-4 rounded">
                <h5 class="mb-3">学生情報</h5>

                <form method="get" action="TestListStudentExecute.action">
                    <div class="row align-items-end">

                        <!-- 学生番号 -->
                        <div class="col-6">
                            <label class="form-label">学生番号</label>
                            <input type="text" class="form-control" name="student_no"
                                   placeholder="学生番号を入力してください"
                                   value="${student_no}">
                        </div>

                        <!-- 検索ボタン -->
                        <div class="col-6 d-flex justify-content-end">
                            <button class="btn btn-secondary mt-4">検索</button>
                        </div>

                    </div>
                </form>
            </div>

            <!-- ▼ 検索結果（科目別・学生別） -->
            <jsp:include page="test_list_student.jsp" />

        </section>
    </c:param>
</c:import>