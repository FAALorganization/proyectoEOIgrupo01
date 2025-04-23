document.addEventListener("DOMContentLoaded", function(event) {
   
    const showNavbar = (toggleId, navId, bodyId, headerId) =>{
    const toggle = document.getElementById(toggleId),
    nav = document.getElementById(navId),
    bodypd = document.getElementById(bodyId),
    headerpd = document.getElementById(headerId)
    
    // Validate that all variables exist
    if(toggle && nav && bodypd && headerpd){
    toggle.addEventListener('click', ()=>{
    // show navbar
    nav.classList.toggle('show')
    // change icon
    toggle.classList.toggle('bx-x')
    // add padding to body
    bodypd.classList.toggle('body-pd')
    // add padding to header
    headerpd.classList.toggle('body-pd')
    })
    }
    }
    
    showNavbar('header-toggle','nav-bar','body-pd','header')
    
    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')
    
    function colorLink(){
    if(linkColor){
    linkColor.forEach(l=> l.classList.remove('active'))
    this.classList.add('active')
    }
    }
    linkColor.forEach(l=> l.addEventListener('click', colorLink))
    
     // Your code to run since DOM is loaded and ready
    });

counter = 0;
const buttonNav = document.getElementById("header-toggle");
buttonNav.addEventListener("click", function(){
    
    if (counter%2 == 0){
        document.querySelectorAll(".nav_name").forEach(link =>{
            link.style.color = "white";
            link.classList.add("textenter");    
        })
        counter += 1;
    }else {
        document.querySelectorAll(".nav_name").forEach(link =>{
            link.style.color = "#252323";
            link.classList.remove("textenter");   
        })
        counter += 1; 
    }    
});

const date = new Date();

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
document.getElementById("actual-date-text").innerHTML = months[date.getMonth()] + " " + date.getFullYear();

const buttonLeft = document.getElementById("left-button");
const buttonRight = document.getElementById("right-button");

const maxIndexMonth = 11;
const minIndexMonth = 0;

actualIndexMonth = date.getMonth();
actualMonth = months[actualIndexMonth];

const inputMonth = document.getElementById("start");

if (date.getMonth().toString().length === 1) {
    stringMonth = "0" + (date.getMonth()+1).toString();
}else {
    stringMonth = (date.getMonth()+1).toString();
}
inputMonth.value = date.getFullYear() + "-" + stringMonth;

inputMonth.addEventListener("input",function(){
    actualIndexMonth = Number(inputMonth.value.split("-")[1]) - 1;
    actualMonth = months[actualIndexMonth];
    document.getElementById("actual-date-text").innerHTML = actualMonth + " " + inputMonth.value.split("-")[0];
    changeDays();
})


actualYear = date.getFullYear();

days = {
    "January":{"names":[],"numbers":[]},
    "February":{"names":[],"numbers":[]},
    "March":{"names":[],"numbers":[]},
    "April":{"names":[],"numbers":[]},
    "May":{"names":[],"numbers":[]},
    "June":{"names":[],"numbers":[]},
    "July":{"names":[],"numbers":[]},
    "August":{"names":[],"numbers":[]},
    "September":{"names":[],"numbers":[]},
    "October":{"names":[],"numbers":[]},
    "November":{"names":[],"numbers":[]},
    "December":{"names":[],"numbers":[]},
}
for (let i = 0; i<12; i++ ) {
    const lastDayDate = new Date(actualYear,i+1,0);
    const lastDayCalendar = lastDayDate.getDate();
    const monthCalendar = lastDayDate.getMonth();
    for (let i = 1; i<=lastDayCalendar; i++) {
        const dayName = (new Date(actualYear,monthCalendar,i)).toLocaleDateString('en-US', {weekday:"long"});
        days[months[monthCalendar]]["names"].push(dayName);
        days[months[monthCalendar]]["numbers"].push(String(i));
    }   
}

daysWeek = {
    "Monday": 1,
    "Tuesday": 2,
    "Wednesday":3,
    "Thursday":4,
    "Friday":5,
    "Saturday":6,
    "Sunday":7
}

daysWeekrev = {
    1:"Monday",
    2:"Tuesday",
    3:"Wednesday",
    4:"Thursday",
    5:"Friday",
    6:"Saturday",
    7:"Sunday"
}

function changeDays(){
    let indexRow = 1;
    let counter = 1;

    //Replace the default number with the appropriate numbers in the month
    for (let i = 0; i<days[actualMonth]["names"].length; i++){
        
        let indexCol = daysWeek[days[actualMonth]["names"][i]];
        let indexDayNumber = days[actualMonth]["numbers"][i];

        document.getElementById("day-" + (counter*indexRow).toString() + "-" + (indexCol).toString()).innerHTML = indexDayNumber.toString();
        document.getElementById("day-" + (counter*indexRow).toString() + "-" + (indexCol).toString()).style.opacity = 1;
        document.getElementById("day-" + (counter*indexRow).toString() + "-" + (indexCol).toString()).style.pointerEvents = "auto";
        document.getElementById("day-" + (counter*indexRow).toString() + "-" + (indexCol).toString()).style.cursor = "pointer";
        
        if (indexCol === 7) {
            counter += 1;
        }    

        if (i === 0) {
            indexColFixFirstDay = indexCol;
        }else if (i === (days[actualMonth]["names"].length-1)) {
            indexColFixLastDay = indexCol;
            indexRowFixLastDay = indexRow*counter;
        }

    }

    //Oculting Starting Month Days:
    for (let j = 1; j<indexColFixFirstDay;j++) {
        document.getElementById("day-1-" + j.toString()).innerHTML = "";
        document.getElementById("day-1-" + j.toString()).style.opacity = 0.5;
        document.getElementById("day-1-" + j.toString()).style.pointerEvents = "none";
    
    }

    //Oculting Ending Month Days:
    for (let k = indexRowFixLastDay; k<=6;k++) {
        for (let m = indexColFixLastDay+1; m<=7; m++) {
            document.getElementById("day-" + k.toString() + "-" + m.toString()).innerHTML = "";
            document.getElementById("day-" + k.toString() + "-" + m.toString()).style.opacity = 0.5;
            document.getElementById("day-" + k.toString() + "-" + m.toString()).style.pointerEvents = "none";
            if (k === 5 && m === 7){
                k=6
                m=0
            }
        }
    }
    indexRow = 1;
}


changeDays();

const weekButton = document.getElementById("week-button");
const dayButton = document.getElementById("day-button");
const monthButton = document.getElementById("month-button");

weekButton.addEventListener("click", function(){
    document.getElementById("month-mode").style.display = "none";
    document.getElementById("week-mode").style.display = "grid";
    
});

monthButton.addEventListener("click", function(){
    document.getElementById("month-mode").style.display = "grid";
    document.getElementById("week-mode").style.display = "none";
});

//Click days:
alert(document.querySelectorAll(".day-month"));
document.querySelectorAll(".day-month").forEach(div => {
    div.addEventListener("click", function(){

     alert(document.querySelectorAll(".day-month"));
        
    });
});


