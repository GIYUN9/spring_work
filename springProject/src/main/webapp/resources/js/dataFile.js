let datas = {};

function init(){
    $.ajax({
        url: 'list.bo',
        success: function(result){
            datas.boardList = result;
        },
        error: function(){
            console.log("아이디 중복체크 ajax통신 실패");
        }
    })
    $.ajax({
        url: 'list.bo',
        success: function(result){
            datas.boardList = result;
        },
        error: function(){
            console.log("아이디 중복체크 ajax통신 실패");
        }
    })
    $.ajax({
        url: 'list.bo',
        success: function(result){
            datas.boardList = result;
        },
        error: function(){
            console.log("아이디 중복체크 ajax통신 실패");
        }
    })
    $.ajax({
        url: 'list.bo',
        success: function(result){
            datas.boardList = result;
        },
        error: function(){
            console.log("아이디 중복체크 ajax통신 실패");
        }
    })
}