<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">

        <section class="me-4">

            <!-- ① タイトル -->
            <h2 class="h3 mb-4 bg-secondary bg-opacity-10 py-3 px-4">
                成績管理
            </h2>

            <!-- ② 完了メッセージ -->
            <div class="alert alert-success mx-3">
                登録が完了しました
            </div>

            <!-- ③・④ リンク -->
            
			<div class="mx-3 mt-4">
			
			    <!-- 戻るリンク -->
			    <a href="TestRegist.action">戻る</a>
			
			    <!-- 間隔をあける -->
			    <span class="ms-4"></span>
			
			    <!-- 成績参照リンク -->
			    <a href="TestList.action">成績参照</a>
			
			</div>
            

        </section>

    </c:param>
</c:import>
