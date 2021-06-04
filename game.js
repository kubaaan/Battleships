
let shipDirection = 'vertical';
let shipLength = 3;

$(document).contextmenu(function(e) {
    e.preventDefault();
    shipDirection = (shipDirection === 'vertical' ? 'horizontal' : 'vertical');
    $(".player-one").children().removeClass('hover');
    $(".player-one").children().removeClass('invalid');
});

$('.player-one .field').hover(  
    applyClassForHover,
    applyClassForHover
);

function applyClassForHover(e) {
    let incrementFactor = (shipDirection === 'vertical'? 10 : 1); 
    let appliedClass = 'hover';
    let hoveredIndex = $(this).index();
    let endIndex = $(this).index() + (shipLength - 1) * incrementFactor ;

    if (shipDirection === 'vertical' && endIndex >= 100){
        appliedClass = 'invalid';
    }

    if ((shipDirection === 'horizontal') && (Math.floor(hoveredIndex/10) != Math.floor(endIndex/10))){
        appliedClass = 'invalid';
    }

    for(var i = 0; i < shipLength; i++){
        let index = $(this).index() + i * incrementFactor + 1;

        if($(".player-one .field:nth-child(" + index + ")").hasClass("deployed")){
            appliedClass = 'invalid';
            break;
        }

    }


    if(e.type === 'mouseenter') {
        for(var i = 0; i < shipLength; i++){
            let index = $(this).index() + i * incrementFactor + 1;

            if ((shipDirection === 'horizontal') && (Math.floor(hoveredIndex/10) != Math.floor((index - 1)/10))){
                break;
            }

            $(".player-one .field:nth-child(" + index + ")").addClass(appliedClass);
        }
    } else {
        $(".player-one").children().removeClass(appliedClass);
    }

}