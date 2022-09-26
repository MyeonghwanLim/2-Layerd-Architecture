<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


 
<!DOCTYPE html>
<html lang="ko">
   <head>
      <meta charset="UTF-8">
      <title>list</title>
     
 
   </head>
   <style type="text/css">
 #wrap{
    display: flex;
    flex-flow: row wrap;
    justify-content: start;
    position: relative;
}
.show{
    transition: .3s ease;
    opacity: 1;
}

.hide{
    transition: .2s ease;
    opacity: 0;
}

[href^="#"]{
    /* href속성을 가진 태그 중 값이 #으로 시작하는 것 선택 */
    width: 100px;
    height: 150px;
    display: block;
    background-color: gray;
    margin: 1rem;
}

[data-group]{
    transition: .3s ease;
    position: absolute;
}
 </style>
   <body>
   <div>
            <!-- 버튼 요소 -->
            <button data-value="all">all</button>
            <button data-value="a">1</button>
            <button data-value="b">2</button>
            <button data-value="c">3</button>
            <button data-value="d">4</button>
            <button data-value="e">5</button>
        </div>
        <div id="wrap">
            <!-- 카드가 되는 요소들 -->
            <!-- 그룹에는 값을 띄어쓰기로 구분하고 all은 공통으로 넣습니다 -->
            <a data-group="all a" href="#1">anchor1</a>
            <a data-group="all b a" href="#2">anchor2</a>
            <a data-group="all a" href="#3">anchor3</a>
            <a data-group="all d" href="#4">anchor4</a>
            <a data-group="all a b c" href="#5">anchor5</a>
            <a data-group="all e" href="#6">anchor6</a>
            <a data-group="all d" href="#7">anchor7</a>
            <a data-group="all c" href="#8">anchor8</a>
            <a data-group="all b" href="#9">anchor9</a>
            <a data-group="all c" href="#10">anchor10</a>
            <a data-group="all a" href="#11">anchor11</a>
            <a data-group="all d" href="#12">anchor12</a>
        </div>
        
        <script type="text/javascript">
        let all = document.querySelectorAll('[data-group]');
        let values = document.querySelectorAll("[data-value]");
        let rowid = 0;
        let colid = 0;

        el.addEventListener('click',function(event){
            values.forEach((el)=>{

                rowid=-1; // 동일하게 row값 -1
                colid=0; // 마찬가지로 col값 0
                let idx = 0; // show 되는 요소들의 인덱스 값

                let val = event.target.dataset.value;
                all.forEach(el=>{
                    let arr = el.dataset.group.split(' ');
                    for(let a of arr){
                        if(a==val){
                            el.setAttribute("class","show");
                            /* 클릭시 정렬 기능 자리 */

                            if(idx%4==0){
                                // 초기 정렬과 동일함
                                rowid++;
                                colid=0;
                            }

                            el.style.transform = `scale3d(1,1,1) translate3d(${110*colid}px, ${160*rowid}px,0px)`;
                            // 보기 쉽게 item.clientHeight+10을 축약함 (초기정렬과 동일함)
                            colid++;
                            idx++; // 초기와 달리 show일때 수동으로 카운트해야하므로 1씩 증가

                            break;
                        } else {
                            el.setAttribute("class","hide");
                                /* 클릭시 정렬 기능 자리 */
                            el.style.transform = `scale3d(0,0,1) translate3d(0px,0px,-1px)`;
                            // hide일때 시작점으로 모이게 함
                        }
                    }
                });
                let col = 0;
                let row = -1;
                // index를 4로 나눈 몫이 0이기 때문에 한자리 비워집니다.
                // 원래 시작자리로 하기위해 -1부터합니다.

                // 초기 정렬
                all.forEach((item, index, list)=>{
                    if(index%4==0) {
                        // 달력에서 주단위로 줄바꿈과 같은 원리
                        // 카드를 4열로 설정
                        row++; // 열을 바꿀때 행 추가
                        col=0; // 열 0으로 초기화
                    }
                    item.style.transform = 
                    `scale3d(1,1,1) translate3d(${col*(item.clientWidth+10)}px, ${row*(item.clientHeight+10)}px, 0px)`;
                    col++;
                    // item(카드)의 너비와 높이값에 여백으로 10만큼 더 함
                });
            });
        });
       
        
        </script>
</body>
</html>
