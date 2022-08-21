window.onload=function () {
    let tableFruit = document.getElementById("table_fruit");
    let trs = tableFruit.rows;
    let i = 1;
    for(; i < trs.length - 1; i++) {
        trs[i].addEventListener("mouseover",backgroundColorTobeBlack);
        trs[i].addEventListener("mouseout",backgroundColorTobeDefault);
        let tds = trs[i].cells;
        tds[1].addEventListener("mouseover",cursorTobePointer);
        tds[1].addEventListener("click",clickShowText);
        // let img = tds[tds.length - 1].firstChild;
        // img.addEventListener("click",deleteTr);

        updateXJ(trs[i]);
    }
    trs[i].addEventListener("onload",updateZJ());
}


function backgroundColorTobeBlack(e) {
    if(e && e.target && e.target.tagName == "TD") {
        let tr = e.target.parentNode;
        tr.style.backgroundColor = "#000080";
        let cells = tr.cells;
        for(let i = 0; i < cells.length; i++) {
            cells[i].style.color = "ivory";
        }

    }
}


function backgroundColorTobeDefault(e) {
    if(e && e.target && e.target.tagName == "TD") {
        let tr = e.target.parentNode;
        tr.style.backgroundColor = "Transparent";
        let cells = tr.cells;
        for(let i = 0; i < cells.length; i++) {
            cells[i].style.color = "dimgray";
        }

    }
}

function  cursorTobePointer(e) {
    if(e && e.target && e.target.tagName == "TD") {
        let tr = e.target;
        tr.style.cursor = "pointer";
    }
}

function  clickShowText(e) {

    if(e && e.target && e.target.tagName == "TD" && e.target.firstChild.nodeType == 3) {
        let td = e.target;
        let price = td.innerText;
        td.innerHTML = "<input type='text' size='15'/>";
        let input = td.firstChild;
        if(input.tagName == "INPUT") {
            input.value = price;
            input.select();
            input.addEventListener("blur",updatePrice);
            input.addEventListener("keydown",checkKeyIsLegal);
        }
    }
}


function updatePrice(e) {

    if(e && e.target && e.target.tagName == "INPUT") {
        let input = e.target;
        let td = input.parentNode;
        let newPrice = input.value;
        td.innerText = newPrice;
        let tr = td.parentNode;
        updateXJ(tr);

    }
}


function updateXJ(tr) {
    let tds = tr.cells;
    let count = tds[2].innerText;
    let newPrice = tds[1].innerText;
    tds[3].innerText = (String) (newPrice * count);
    updateZJ();
}

function updateZJ() {
    let table = document.getElementById("table_fruit");
    let trs = table.rows;
    let sum = 0;
    let i = 1;
    for(; i < trs.length - 1; i++) {
        sum += parseInt(trs[i].cells[3].innerText);
    }

    trs[i].cells[1].innerText = "" + sum;
}



function deleteTr(e) {
    if(e && e.target && e.target.tagName == "IMG") {
        if(window.confirm("确认删除该条数据？")) {
            let td = e.target.parentElement;
            let tr = td.parentElement;
            let table = tr.parentElement;
            table.deleteRow(tr.rowIndex);
            updateZJ();
        }

    }
}

function checkKeyIsLegal(e) {
    let key = e.keyCode;
    console.log(key);
    if( (key >= 48 && key <= 57 ) || key == 8 || key == 13) {
        if(key == 13)
            e.target.blur();
    } else {
        e.preventDefault();
    }
}


function delFruit(id) {
    if(confirm("确认删除？")) {
        window.location.href = "delete?id=" + id;
    }

}

function changePage(pageOpt) {
    window.location.href = "changePage?pageOpt=" + pageOpt;
}

