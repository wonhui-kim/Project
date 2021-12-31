const $btnSports = $(".btn_sports");
const $btnFr = $(".btn_fr");
const $btnGoal = $(".btn_goal");
const $btnWay = $(".btn_way");
const $optionHashtag = $(".option_hashtag");
const $optionSports = $(".option_sports");
const $optionFr = $(".option_fr");
const $optionGoal = $(".option_goal");
const $optionWay = $(".option_way");
const $takeHt = $(".take_ht");

$btnSports.on('click', function (){
    if($optionSports.children(".selected").length >= 5){ //선택 개수 확인
        if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 삭제
            $(this).removeClass('selected');
            const btnText = $(this).text();
            $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
                $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
            }
            return;
        } else {
            alert("최대 다섯 개 선택 가능");
            return;
        }
    }
    if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 삭제
        $(this).removeClass('selected');
        const btnText = $(this).text();
        $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
            $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
        }
    } else { //선택 시 selected 클래스 없다면 추가 후 해시태그 상자에 div 추가
        $(this).addClass('selected');
        const btnText = $(this).text();
        const createHashtag = $(`<button class="hashtag_btn btn">#${btnText}</button>`);
        $optionHashtag.append(createHashtag);
    }
})

$btnFr.on('click', function (){
    if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 삭제
        $(this).removeClass('selected');
        const btnText = $(this).text();
        $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
            $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
        }
    } else { //선택 시 selected 클래스 없다면 추가 후 해시태그 상자에 div 추가
        if($optionFr.children('.selected')){ //다른 해시태그가 이미 선택되어 있다면 다른 해시태그 삭제
            const btnText = $optionFr.children('.selected').text();
            $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            $optionFr.children('.selected').removeClass('selected');
        }
        $(this).addClass('selected');
        const btnText = $(this).text();
        const createHashtag = $(`<button class="hashtag_btn btn">#${btnText}</button>`);
        $optionHashtag.append(createHashtag);
    }
})

$btnGoal.on('click', function (){
    if($optionGoal.children(".selected").length >= 3){ //선택 개수 확인
        if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 삭제
            $(this).removeClass('selected');
            const btnText = $(this).text();
            $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
                $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
            }
            return;
        } else {
            alert("최대 세 개 선택 가능");
            return;
        }
    }
    if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 삭제
        $(this).removeClass('selected');
        const btnText = $(this).text();
        $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
            $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
        }
    } else { //선택 시 selected 클래스 없다면 추가 후 해시태그 상자에 div 추가
        $(this).addClass('selected');
        const btnText = $(this).text();
        const createHashtag = $(`<button class="hashtag_btn btn">#${btnText}</button>`);
        $optionHashtag.append(createHashtag);
    }
})

$btnWay.on('click', function (){
    if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 삭제
        $(this).removeClass('selected');
        const btnText = $(this).text();
        $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
            if($(this).text() == `#${btnText}`) $(this).remove();
        })
        if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
            $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
        }
    } else { //선택 시 selected 클래스 없다면 추가 후 해시태그 상자에 div 추가
        if($optionWay.children('.selected')){ //다른 해시태그가 이미 선택되어 있다면 다른 해시태그 삭제
            const btnText = $optionWay.children('.selected').text();
            $(".option_hashtag button").each(function (){ //해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
                if($(this).text() == `#${btnText}`) $(this).remove();
            })
            $optionWay.children('.selected').removeClass('selected');
        }
        $(this).addClass('selected');
        const btnText = $(this).text();
        const createHashtag = $(`<button class="hashtag_btn btn">#${btnText}</button>`);
        $optionHashtag.append(createHashtag);
    }
})

$(document).on("click", ".hashtag_btn", function (){ //해시태그 상자에서 선택 시 대표해시태그 상자로 이동, 해시태그 상자에서 selected
    if($(this).hasClass('selected')){ //선택 시 selected 클래스 있다면 클래스 삭제
        $(this).removeClass('selected');
        const btnText = $(this).text();
        $(".take_ht button").each(function (){ //대표 해시태그 상자 확인 후 삭제
            if($(this).text() == `${btnText}`) $(this).remove();
        })
        if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
            $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
        }
    } else {
        if($takeHt.children().length >= 3){ //대표해시태그 3개 이상이라면 취소
            alert("최대 세 개 선택 가능");
            return;
        }
        if($takeHt.children().length == 0) $takeHt.text(""); //대표해시태그 상자 문장 삭제
        const btnText = $(this).text();
        const createHashtag = $(`<button class="take_ht_btn">${btnText}</button>`);
        $takeHt.append(createHashtag);
        $(this).addClass('selected');
    }
})

$(document).on("click", ".take_ht_btn", function (){ //대표해시태그 상자에서 선택 시 해시태그 상자로 이동(선택 취소)
    const btnText = $(this).text();
    $(".option_hashtag button").each(function (){ //대표 해시태그 상자 확인 후 삭제
        if($(this).text() == `${btnText}`) $(this).removeClass('selected');
    })
    $(this).remove();
    if($takeHt.children().length == 0){ //대표 해시태그 상자 비어있으면 텍스트 입력
        $takeHt.text("아래 해시태그를 클릭하여 대표 해시태그를 선정하세요.");
    }
})
















