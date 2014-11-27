<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${pages > 1}">
                    <div style="margin-top: 50px" class = "row">
                        <div class="pagination-centered">
                            <ul class="pagination">
                                <c:choose>
                                    <c:when test="${1 == currentPage}">
                                        <li class="arrow"><a href="">&laquo;</a></li>
                                    </c:when>
                                    <c:when test="${1 != currentPage}">
                                        <li class="arrow"><a href="?page=${currentPage - 1}">&laquo;</a></li>
                                    </c:when>
                                </c:choose>
                                <c:forEach var="i" begin="1" end="${pages}">
                                    <c:choose>
                                        <c:when test="${i == currentPage}">
                                            <li class="current"><a href="">${i}</a></li>
                                        </c:when>
                                        <c:when test="${i != currentPage}">
                                             <li><a href="?page=${i}">${i}</a></li>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${pages == currentPage}">
                                        <li class="arrow"><a href="">&raquo;</a></li>
                                    </c:when>
                                    <c:when test="${pages != currentPage}">
                                        <li class="arrow"><a href="?page=${currentPage + 1}">&raquo;</a></li>
                                    </c:when>
                                </c:choose>
                            </ul>
                        </div>
                    </div>
                </c:if>