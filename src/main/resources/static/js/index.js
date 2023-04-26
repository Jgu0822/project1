
// 자동갤러리

const galleryLi = document.querySelectorAll('.gallery-con>ul>li');
const itemLi = document.querySelectorAll('.item-con>ul>li');

let i = -1;

function autoGallery(){
  // i가 갤러리 이미지 총 갯수에 도달할 경우 시작번호(0)로 세팅
  if(i>=galleryLi.length-1){
    i=-1;
  }
  i++;

  console.log(`i=>${i}`);

  galleryLi.forEach((el,idx)=>{
    if(i==idx){
      el.classList.add('fadeLi');
    } else {
      el.classList.remove('fadeLi');
    }
  })

  itemLi.forEach((el,idx)=>{
    if(i==idx){
      el.classList.add('on');
    } else {
      el.classList.remove('on');
    }
  })

}
// 3초마다 autoGallery 함수 실행
let setIn = setInterval(autoGallery,3000);

// 즉시실행 함수 (페이지 로드시 바로 실행)
(function(){
  autoGallery();
})()

const itemUl = document.querySelector('.item-con>ul');

itemUl.addEventListener('mouseover',itemUlfn);
itemUl.addEventListener('mouseout',itemUlfn);

function itemUlfn(e){
  itemLi.forEach((el,idx)=>{
    if(e.target==el){
      if(e.type=='mouseover'){
        console.log('e.type : '+e.type);
        clearInterval(setIn);
      } else {
        console.log('e.type : '+e.type);
        setIn = setInterval(autoGallery,3000);
      }
    }
  })
}

itemUl.addEventListener('click',galleryFn);

function galleryFn(e){
  itemLi.forEach((el,idx)=>{
    if(e.target==el){
      console.log('e.type : '+e.type);
      el.classList.add('on');

      galleryLi.forEach((el2,idx2)=>{
        if(idx==idx2){
          el2.classList.add('fadeLi');
        } else {
          el2.classList.remove('fadeLi');
        }
      })
    } else {
      el.classList.remove('on');
    } 
    i = idx;
  })
}

const arrow = document.querySelectorAll('span.arrow');

arrow[0].addEventListener('click',(e)=>{
  if(i<=0){
    i=galleryLi.length;
  }

  i--;

  console.log(i)

  galleryLi.forEach((el,idx)=>{
      if(i==idx){
        // '.fadeLi' 클래스 추가
        el.classList.add('fadeLi')
      } else {
        el.classList.remove('fadeLi')
      }
    })

    itemLi.forEach((el,idx)=>{
      if(i==idx){
        el.classList.add('on')
      } else {
        el.classList.remove('on')
      }
    })

  })

  arrow[1].addEventListener('click',(e)=>{
    if(i>=galleryLi.length-1){
      i=-1;
    }
    
    i++;

    console.log(i)

    galleryLi.forEach((el,idx)=>{
      if(i==idx){
        el.classList.add('fadeLi')
      } else {
        el.classList.remove('fadeLi')
      }
    })

    itemLi.forEach((el,idx)=>{
      if(i==idx) {
        el.classList.add('on')
      } else {
        el.classList.remove('on')
      }
    })
  })
