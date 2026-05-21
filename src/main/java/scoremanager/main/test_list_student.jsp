<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ▼ 科目別成績一覧 -->
<c:if test="${not empty subject_test_list}">
    <div class="border mx-3 mb-3 py-3 px-4 rounded">
        <h5 class="mb-3">成績一覧（科目）</h5>

        <table class="table table-bordered">
            <thead class="table-light">
                <tr>
                    <th>入学年度</th>
                    <th>クラス</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>1回</th>
                    <th>2回</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="t" items="${subject_test_list}">
                    <tr>
                        <td>${t.entYear}</td>
                        <td>${t.classNum}</td>
                        <td>${t.studentNo}</td>
                        <td>${t.studentName}</td>

                        <!-- ★ 修正ポイント：Map ではなく Bean の getter を使う -->
                        <td>${t.point1 != null ? t.point1 : '-'}</td>
                        <td>${t.point2 != null ? t.point2 : '-'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<!-- ▼ 学生名（成績がある時もない時も表示） -->
<c:if test="${not empty student_name}">
    <div class="mx-3 mb-2">
        <strong>氏名：</strong> ${student_name}（${student_no}）
    </div>
</c:if>

<!-- ▼ 学生検索エラー（成績が 0 件のとき） -->
<c:if test="${not empty student_message}">
    <div class="alert alert-warning py-2 px-3 mx-3 mb-3">
        ${student_message}
    </div>
</c:if>

<!-- ▼ 学生別成績一覧（成績がある時だけ表示） -->
<c:if test="${not empty student_test_list}">
    <div class="border mx-3 mb-3 py-3 px-4 rounded">
        <h5 class="mb-3">成績一覧（学生）</h5>

        <table class="table table-bordered">
            <thead class="table-light">
                <tr>
                    <th>科目名</th>
                    <th>科目コード</th>
                    <th>回数</th>
                    <th>点数</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="t" items="${student_test_list}">
                    <tr>
                        <td>${t.subjectName}</td>
                        <td>${t.subjectCd}</td>
                        <td>${t.num}</td>
                        <td>${t.point}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>