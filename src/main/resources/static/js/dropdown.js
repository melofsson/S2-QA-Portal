$(document).ready(function() {

});

function showIcons(id) {
    $('#statusicon'+id).css('visibility','visible');
    $('#statusicon'+id).css('opacity','1');
    $('#jiraicon'+id).css('visibility','visible');
    $('#jiraicon'+id).css('opacity','1');
}

function hideIcons(id) {
    $('#statusicon'+id).css('visibility','hidden');
    $('#statusicon'+id).css('opacity','0');
    $('#jiraicon'+id).css('visibility','hidden');
    $('#jiraicon'+id).css('opacity','0');
}

function showJiraOverlay(){
    $(".jiraOverlay").css('visibility','visible');
    $(".jiraOverlay").css('opacity','1');
    return false;
}

function closeJiraOverlay(){
    $(".jiraOverlay").css('visibility','hidden');
    $(".jiraOverlay").css('opacity','0');
    return false;
}

$(".arrange-dropdown-menu li a").click(function(){
        var choiceString = $(this).text();

        showCorrectDiv(choiceString);
        $(".btn:first-child").text($(this).text());
        $(".btn:first-child").val(choiceString);

    });

function setStatusNotFixed(id){
    $('#'+id).css('border-left','8px solid red');
    return false;
}

function setStatusWorkingOn(id){
    $('#'+id).css('border-left','8px solid orange');
    return false;
}

function setStatusResolved(id){
    $('#'+id).css('border-left','8px solid green');
    return false;
}

function showCorrectDiv(choice){

    switch (choice) {
        case "Error message":
            $("#error-messages-div").css('display','block');
            $("#failing-tests-div").css('display','none');
            break;
        case "Failing test":
            $("#error-messages-div").css('display','none');
            $("#failing-tests-div").css('display','block');
            break;
    }

    $("#main-section-title").text(choice.toUpperCase() + "S");
}

$(".errormessages-inner").on("click", function(){
    if ($(this).hasClass('selected-div')) {
        $(this).removeClass('selected-div');
    } else {
        $(this).addClass('selected-div');
    }
});

$(".testnames-inner").on("click", function(){
    if ($(this).hasClass('selected-div')) {
        $(this).removeClass('selected-div');
    } else {
        $(this).addClass('selected-div');
    }
});



//$(".draggable").draggable();

