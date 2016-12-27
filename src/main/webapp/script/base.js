/**
 * 为文档加载完成后添加函数
 * @param func
 */
function addOnLoadEvent(func) {
    var oldOnLoad = window.onload;
    if (typeof oldOnLoad != "function") {
        window.onload = func;
    } else {
        window.onload = function() {
            oldOnLoad();
            func();
        }
    }

}

function alertContent(content) {
    alert(content);
}