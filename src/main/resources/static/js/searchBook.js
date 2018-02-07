/**
 * Created by cait on 2018-02-07.
 */
$(function () {
    /**
     * 搜索
     */
    $('.search').click(function () {
        var wd = $("#wd").val();
        $.get("/rawler/search", {key:wd}, function (data) {
            console.log(data);
        })
    })
})