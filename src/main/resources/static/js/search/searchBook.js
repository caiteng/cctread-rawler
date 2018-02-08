/**
 * Created by cait on 2018-02-07.
 */
$(function () {
    /**
     * 搜索
     */
    $('.search').click(function () {
        var wd = $("#wd").val();
        var url = "/search";
        if (wd) {
            url += "?key=" + wd;
        }
        window.location.href = url;

    })
    /**
     * 添加书本
     */
    $('.createBook').click(function () {
        var href = $(this).attr("data-href");
        var name = $(this).parent().parent().find("td:eq(1)").text();
        var img = $(this).parent().parent().find("img").attr("src");
        var author = $(this).parent().parent().find("td:eq(2)").text();
        $.get("/rawler/createBook", {href: href, name: name, img: img, author: author}, function (data) {
            alert(data);
        })
    })


})