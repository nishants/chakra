$(document).ready(function () {
    var editor = CodeMirror.fromTextArea(document.getElementById("editor1"), {
        lineNumbers: true,
        matchBrackets: true,
        tabSize: 2,
        mode: "text/x-java"
    });
    window.editor = editor;

    var showLoader = function(visible){
        var loader = $("#loader");
        visible ? loader.show() : loader.hide();
    };
    var showConsole = function (response) {
        $("#console-output").html(response.content.console.join("<br>"));
        showLoader(false);
    };

    var showError = function (response) {
        console.log(JSON.stringify(response))
    };

    var compileAndRun = function (code, success, failure) {
        var data =function(code){
            var response = {
                content: {
                    mainClass: "HelloWorld",
                    javaFiles: [{className: "HelloWorld", javaCode: code}]
                }
            };
            return JSON.stringify(response);
        };

        $.ajax({
            type: "POST",
            contentType: "application/json;",
            url: "/runner/main",
            data: data(code),
            success: success,
            error: failure
        });
    };


    $("#run-button").click(function () {
        showLoader(true);
        compileAndRun(editor.getValue().replace("\n", ""), showConsole, showError)
    });

    showLoader(false);
});