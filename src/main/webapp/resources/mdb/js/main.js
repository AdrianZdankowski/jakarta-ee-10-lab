
function initInputs() {
    document.querySelectorAll('.form-outline').forEach((formOutline) => {
        new mdb.Input(formOutline).init();
    });
}

function fixRadio() {
    document.querySelectorAll('input[type="radio"]').forEach((input) => {
        input.classList.toggle('form-check-input');
    })
}

function onReset() {
    setTimeout(initInputs, 0);
}

function receive(msg, channel, event) {
    let data = JSON.parse(event.data);
    appendLine(data.from + ': ' + data.content);
}

function appendLine(line) {
    let textarea = document.getElementById('messageContainer');
    if (textarea) {
        textarea.value += line + '\n';
        // Auto-scroll to bottom
        textarea.scrollTop = textarea.scrollHeight;
    }
}

document.addEventListener("DOMContentLoaded", function(){
    initInputs();
    fixRadio();
});
