
let shipDirection = 'vertical';
let shipLength = 3;

$(document).contextmenu(function(e) {
    e.preventDefault();
    shipDirection = (shipDirection === 'vertical' ? 'horizontal' : 'vertical');
    $(".player-one").children().removeClass('hover');
});

$('.player-one .field').hover(  
    function(){   
        let incrementFactor = (shipDirection === 'vertical'? 10 : 1); 
        for(var i = 0; i < shipLength; i++){
            let index = $(this).index() + i * incrementFactor + 1;
            $(".player-one .field:nth-child(" + index + ")").addClass('hover');
        }
    }, 
    function() {  
        let incrementFactor = (shipDirection === 'vertical'? 10 : 1);    
        for(var i = 0; i < shipLength; i++){
            let index = $(this).index() + i * incrementFactor + 1;
            $(".player-one .field:nth-child(" + index + ")").removeClass('hover');
        }
    }
);