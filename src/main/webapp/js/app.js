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

    var showSuccess = function (response) {
        $("#console-output").html(response.content.console.join("<br>"));
    }

    var showError = function (response) {
        $("#error-output").html(response.error);
    };

    var resetConsole = function () {
        $("#console-output").html("");
        $("#error-output").html("")
    };

    var showConsole = function (response) {
        response.error ? showError(response) : showSuccess(response);
        showLoader(false);
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
        resetConsole();
        compileAndRun(editor.getValue().replace("\n", ""), showConsole, showError)
    });

    showLoader(false);
});
