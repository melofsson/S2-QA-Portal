$(document).ready(function() {

});

$(".dropdown-menu li a").click(function(){
        var choiceString = $(this).text();

        showCorrectDiv(choiceString);
        $(".btn:first-child").text($(this).text());
        $(".btn:first-child").val(choiceString);

    });

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

