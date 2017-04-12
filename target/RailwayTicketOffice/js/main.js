var globalResult = [];
var sum = 0;
var departureDate = "";
var class1 = 1;
var class2 = 2;
var seatsCount = 0;
var selected = [];
var flag = true;

var main = {

    log_in: function () {
        var email = document.getElementById("email").value;
        var pass = document.getElementById("pass").value;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/APIHandlerServlet",
            data: {requestType: "login", email: email, password: pass},
            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - log_in, data: ");
                    console.log(data);

                    $("#signIn").hide(1000);
                    $("#reg_btn").hide(1000);
                    $("#signOut").show(1000);

                    document.getElementById("nameExit").value = data.name;
                    document.getElementById("surnameExit").value = data.surname;

                    if (data.isAdmin) {
                        $("#responsive-menu ul").append("<li><a href='#' onclick='main.showUsers()' >Управление</a></li>");
                    }
                } else if (data.errorCode == 2) {
                    alert("Проверти корректность написания логина или пароля!")
                } else if (data.errorCode == 5) {
                    alert("Неправильно введен логин или пароль!")
                } else if (data.errorCode == 9) {
                    alert("Данный пользователь заблокирован!")
                } else {
                    alert("Ошибка, errorCode - не определен!")
                }
            },
            error: function (data) {
                alert("Ошибка входа, блок: error!");
                console.log(data);
            }
        });
    },

    registration: function () {
        var email = document.getElementById("emailReg").value;
        var name = document.getElementById("nameReg").value;
        var surname = document.getElementById("surnameReg").value;
        var pass = document.getElementById("passReg").value;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/APIHandlerServlet",
            data: {
                requestType: "registration", email: email, name: name, surname: surname,
                password: pass
            },
            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - registration, data: ");
                    console.log(data);

                    $("#signUp").hide(1000);
                    $("#signOut").show(1000);

                    document.getElementById("nameExit").value = data.name;
                    document.getElementById("surnameExit").value = data.surname;
                } else if (data.errorCode == 3) {
                    alert("Проверти правильность заполнения регистрационных полей!")
                } else if (data.errorCode == 5) {
                    alert("Пользователь с таким электроным адресом уже существует!")
                } else {
                    alert("Ошибка, errorCode - не определен!");
                }
            },
            error: function (data) {
                alert("Ошибка регистрации, блок: error!");
                console.log(data);
            }
        });
    },

    exit: function () {
        $("#signOut").hide(1000);
        $("#signIn").show(1000);
        $("#reg_btn").show(1000);
        document.getElementById("email").value = "";
        document.getElementById("pass").value = "";
        $("#ts_res").hide(500);
        $("#tbody").empty();

        main.close();
        //location.reload();
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/APIHandlerServlet",
            data: {requestType: "exit"},
            success: function (data) {
                alert("До свидания!")
            }
        });
    },

    search: function () {

        var dep_st = document.getElementById("dep_st").value;
        var arr_st = document.getElementById("arr_st").value;
        var date = document.getElementById("datepicker").value;
        var time = document.getElementById("dep_time").value;
        var currentDate = new Date();
        var enteredDate = new Date(date);
        currentDate.setHours(0, 0, 0, 0);
        departureDate = date;

        if (enteredDate < currentDate || enteredDate > currentDate.setMonth(currentDate.getMonth() + 1)) {
            alert("Выбрано недопустимое значение даты.")
            return false;
        }

        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/APIHandlerServlet",
            data: {requestType: "search", dep_st: dep_st, arr_st: arr_st, date: date, time: time},
            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - search, data: ");
                    console.log(data);

                    $("#ts_res").show();
                    $("#tbody").empty();

                    for (var i = 0; i < Object.keys(data).length; i++) {

                        globalResult[i] = data[i];
                        var result = data[i];
                        var y = (i % 2 == 0) ? '' : 2;

                        $("#tbody").append("<tr id='searchTR" + i + "' class='vToolsDataTableRow'" + y + "'></tr>");

                        $("#tbody > #searchTR" + i).append("<td class='num'>" + result.train + "</td>");

                        $("#tbody > #searchTR" + i).append("<td class='stations'>" + result.dep_st + "<br>"
                            + result.arr_st + "</td>");

                        $("#tbody > #searchTR" + i).append("<td class='date'><div> Отправление <span>" + result.dep_date
                            + "</span></div><p class='clear'></p><div>Прибытие <span>" + result.arr_date
                            + "</span></div><p class='clear'></p></td>");

                        $("#tbody > #searchTR" + i).append("<td class='time'>" + result.dep_time + "<br>"
                            + result.arr_time + "</td>");

                        $("#tbody > #searchTR" + i).append("<td class='dur'>" + result.travel_time + "</td>");

                        $("#tbody > #searchTR" + i).append("<td class='place'></td>");

                        $("#searchTR" + i + "> .place").append("<div title='Сидячий первого класса'>"
                            + "<i class='' style='margin-left: 0px; white-space: nowrap;'>С1</i><b>" + result.free1st
                            + "</b><button type='button' onclick='main.choose(" + class1 + ", " + result.train + ", " + result.dep_st_seq
                            + ", " + result.arr_st_seq + ", " + departureDate + ", " + 1 + ")'>Выбрать</button></div>");

                        $("#searchTR" + i + "> .place").append("<div title='Сидячий второго класса'>"
                            + "<i class='' style='margin-left: 0px; white-space: nowrap;'>С2</i><b>" + result.free2nd
                            + "</b><button type='button' onclick='main.choose(" + class2 + ", " + result.train + ", " + result.dep_st_seq
                            + ", " + result.arr_st_seq + ", " + departureDate + ", " + 2 + ")'>Выбрать</button></div>");

                        $("#tbody").append("<br>");

                    }
                } else if (data.errorCode == 7) {
                    $("#ts_res").hide();
                    $("#tbody").empty();
                    alert("Недопустимое значение полей ввода 'Откуда' / 'Куда'!")
                } else if (data.errorCode == 8) {
                    $("#ts_res").hide();
                    $("#tbody").empty();
                    alert("По вашему запросу ничего не найдено!")
                } else if (data.errorCode == 11) {
                    $("#ts_res").hide();
                    $("#tbody").empty();
                    alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                } else if (data.errorCode == 9) {
                    $("#ts_res").hide();
                    $("#tbody").empty();
                    alert("Извините, но вас заблокировали!")
                } else {
                    alert("Ошибка, errorCode - не определен!");
                }
            },
            error: function (data) {
                alert("Ошибка поиска, блок: error!");
                console.log(data);
            }
        });

    },

    choose: function (carriage_class, train_number, dep_st_seq, arr_st_seq) {

        $.ajax({
                type: "GET",
                dataType: "json",
                url: "/APIHandlerServlet",
                data: {
                    requestType: "choose",
                    train_number: train_number,
                    dep_date: departureDate,
                    dep_st_seq: dep_st_seq,
                    arr_st_seq: arr_st_seq,
                    carriage_class: carriage_class
                },
                success: function (data) {
                    if (data.errorCode == undefined) {
                        console.log("Method - choose, data: ");
                        console.log(data);

                        $("#background_div").show();

                        $("body").append("<div class='vToolsPopupCoachScheme' style='z-index: 1004; position: absolute; "
                            + "top: 1055px; opacity: 1; width: 962px; visibility: visible; left: 183px; height: auto;"
                            + "background: #f1f2f4; border-radius: 3px; margin: 10px; box-shadow: 0 10px 15px 5px #6c6e6f;'></div>");

                        $(".vToolsPopupCoachScheme").append("<div class='vToolsPopupHeader'></div>");

                        $(".vToolsPopupHeader").append("<button type='button' id='close_btn' onclick='main.close()' "
                            + "style='border: none;'></button>");

                        $(".vToolsPopupHeader").append("<span id='chooseHeader'></span>");

                        $("#chooseHeader").append("<span class='title'> Поезд <strong>" + train_number + "</strong>, Вагоны: </span>");

                        $("#chooseHeader").append("<span class='coaches'></span>");

                        for (var j = 0; j < Object.keys(data).length; j++) {
                            var result = data[j];
                            var carriage = result.carriage_number;
                            var freeSeats = result.free_seats;

                            $(".coaches").append("<a class='active' href='#" + carriage + "' onclick='main.updateSeats("
                                + train_number + ", " + result.carriage_number + ", " + carriage_class
                                + ", " + dep_st_seq + ", " + arr_st_seq + ", " + departureDate + ")'>" + carriage + "<b>" + freeSeats + "</b></a>");


                        }

                        $(".vToolsPopupHeader").append("<div id='clearBoth' style='clear: both;'></div>");

                        $(".vToolsPopupHeader").append("<div class='date'>" + departureDate + "</div>");

                        $(".vToolsPopupCoachScheme").append("<div class='vToolsPopupContent' style='overflow: auto; height: auto;'></div>");

                        $(".vToolsPopupContent").append("<div id='ts_chs_scheme' class='coach-scheme'></div>");

                        $("#ts_chs_scheme").append("<div class='wagon-floors'></div>");

                        $(".wagon-floors").append("<div class='floor-floor1' style='width: 708px; height: 155px'>");

                        $(".vToolsPopupContent").append("<br>");

                        $(".vToolsPopupContent").append("<div id='ts_chs_tbl' style='opacity: 1;'></div>");

                        $("#ts_chs_tbl").append("<table class='vToolsDataTableChoose' cellspacing='1' cellpadding='0' border='0' width='100%'></table>");

                        $(".vToolsDataTableChoose").append("<thead><tr>"
                            + "<th class='th1'>№</th>"
                            + "<th class='th2'>Место</th>"
                            + "<th class='th3'>Опции</th>"
                            + "<th class='th4'>Услуги</th>"
                            + "<th class='th5'>Цена (грн)</th>"
                            + "</tr></thead>");

                        $(".vToolsDataTableChoose").append("<tbody id='tbodyChoose'></tbody>");

                    } else if (data.errorCode == 11) {
                        alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                    } else if (data.errorCode == 9) {
                        alert("Извините, но вас заблокировали!")
                    } else {
                        alert("Ошибка, errorCode - не определен!");
                    }
                },
                error: function (data) {
                    alert("Ошибка выбора, блок: error!");
                    console.log(data)
                }
            }
        );
    },

    updateSeats: function (train_number, carriage, carriage_class, dep_st_seq, arr_st_seq) {

        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/APIHandlerServlet",
            data: {
                requestType: "updateSeats",
                train_number: train_number,
                dep_date: departureDate,
                dep_st_seq: dep_st_seq,
                arr_st_seq: arr_st_seq,
                carriage: carriage,
                carriage_class: carriage_class
            },
            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - updateSeats, data: ");
                    console.log(data);

                    $("tr[id^='chooseTR']").remove();
                    $("#info-span").remove();
                    $(".complex_btn").remove();

                    var seats = [];
                    seats = data.seats;

                    var place = 1;
                    var pointX = 78;
                    var pointY = 5;

                    if (carriage_class == 1) {
                        for (var out = 0; out < 4; out++, pointY += 30, pointX = 78) {
                            if (out == 2) pointY = 83;
                            for (var inn = 0; inn < 10; inn++, pointX += 57, place++) {

                                if (seats.indexOf(place) === -1) {

                                    $(".floor-floor1").append("<div place='" + place + "' class='place-fr' title='Место: "
                                        + place + "' aria-label='Место: " + place + "'role='button' style=' position: absolute; left: "
                                        + pointX + "px; top: " + pointY + "px; width: 32px; height: 25px; line-height: 25px;"
                                        + " display: block; font-size: 12px;' onclick='main.selectSeat(" + place + ", " + carriage + ", " + data.price + ", " + train_number + ", " + dep_st_seq + ", " + arr_st_seq + ")'>" + place + "</div>");

                                } else {

                                    $(".floor-floor1").append("<div place='" + place + "' class='place-oc' title='Место: "
                                        + place + "' aria-label='Место: " + place + "' aria-disabled='true' role='button' "
                                        + "style=' position: absolute; left: "
                                        + pointX + "px; top: " + pointY + "px; width: 32px; height: 25px; line-height: 25px;"
                                        + " display: block; font-size: 12px;'>" + place + "</div>");

                                }
                            }
                        }

                    } else if (carriage_class == 2) {
                        for (var out = 0; out < 4; out++, pointY += 30, pointX = 78) {
                            if (out == 2) pointY = 83;
                            for (var inn = 0; inn < 14; inn++, pointX += 40, place++) {
                                if (seats.indexOf(place) === -1) {

                                    $(".floor-floor1").append("<div place='" + place + "' class='place-fr' title='Место: "
                                        + place + "' aria-label='Место: " + place + "'role='button' style='  position: absolute; left: "
                                        + pointX + "px; top: " + pointY + "px; width: 32px; height: 25px; line-height: 25px;"
                                        + " display: block; font-size: 12px;' onclick='main.selectSeat(" + place + ", " + carriage + ", " + data.price + ", " + train_number + ", " + dep_st_seq + ", " + arr_st_seq + ")'>" + place + "</div>");

                                } else {

                                    $(".floor-floor1").append("<div place='" + place + "' class='place-oc' title='Место: "
                                        + place + "' aria-label='Место: " + place + "' aria-disabled='true' role='button'"
                                        + " style=' position: absolute; left: "
                                        + pointX + "px; top: " + pointY + "px; width: 32px; height: 25px; line-height: 25px;"
                                        + " display: block; font-size: 12px;'>" + place + "</div>");

                                }
                            }
                        }
                    }

                    $(".floor-floor1").append("<div class='wall' style='left: 33px; top: 0px; height: 55px; line-height: 65px;'></div>");
                    $(".floor-floor1").append("<div class='wall' style='left: 633px; top: 0px; height: 55px; line-height: 65px;'></div>");
                    $(".floor-floor1").append("<div class='wall' style='left: 673px; top: 0px; height: 55px; line-height: 65px;'></div>");
                    $(".floor-floor1").append("<div class='wall' style='left: 33px; top: 90px; height: 55px; line-height: 65px;'></div>");
                    $(".floor-floor1").append("<div class='wall' style='left: 633px; top: 90px; height: 55px; line-height: 65px;'></div>");
                    $(".floor-floor1").append("<div class='wall' style='left: 673px; top: 90px; height: 55px; line-height: 65px;'></div>");
                    $(".floor-floor1").append("<div class='toilet' style='left: 638px; top: 5px; width: 32px; height: 55px; line-height: 55px;'></div>");
                    $(".floor-floor1").append("<div class='toilet' style='left: 638px; top: 85px; width: 32px; height: 55px; line-height: 55px;'></div>");
                    $(".floor-floor1").append("<div class='bag' style='left: 38px; top: 95px; width: 32px; height: 55px; line-height: 55px;'></div>");

                } else if (data.errorCode == 11) {
                    alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                } else if (data.errorCode == 9) {
                    alert("Извините, но вас заблокировали!")
                } else {
                    alert("Ошибка, errorCode - не определен!");
                }
            },
            error: function (data) {
                alert("Ошибка обновления мест, блок: error!");
                console.log(data);
            }
        });
    },

    selectSeat: function (place, carriage, price, train_number, dep_st_seq, arr_st_seq) {

        if (selected.indexOf(place) != -1) {

            $("div[place=" + place + "]").attr("class", "place-fr");
            $("#chooseTR" + place).remove();

            if (selected.indexOf(place) === 0) {
                selected.splice(0, 1);
            } else {
                selected.splice(selected.indexOf(place), 1);
            }

            $("#summa").text(price * selected.length + "грн");

        } else {

            selected.push(place);
            var y = (place % 2 == 0) ? '' : 2;

            $("div[place=" + place + "]").attr("class", "place-bl");

            $("#tbodyChoose").append("<tr id='chooseTR" + place + "' class='vToolsDataTableRow" + y + "'></tr>");

            $("#chooseTR" + place).append("<td class='num'>" + selected.length + "</td>");

            $("#chooseTR" + place).append("<td class='place' style='opacity: 1;'>Вагон: " + carriage
                + "<br> Место: " + place + "</td>");

            $("#chooseTR" + place).append("<td class='option' style='opacity: 1;'><div class='fio'>"
                + "<form id='fio_form' method='post' action=''><label>Фамилия<input class='lastname'"
                + " name='lastname' type='text' pattern='[а-яА-ЯёЁ]' required title='Можно использовать только кириллицу'></label>"
                + "<label>Имя<input class='firstname' name='firstname' type='text' pattern='[а-яА-ЯёЁ]' "
                + "required title='Можно использовать только кириллицу'></label>"
                + "</div></td></form>");

            $("#chooseTR" + place).append("<td class='service' style='opacity: 1;'></td>");

            $("#chooseTR" + place).append("<td class='price' style='opacity: 1;'>" + price + "</td>");

            $("#chooseTR" + place).append("<br>");

        }
        if (selected.length === 1 && flag) {

            $("#ts_chs_tbl").append("<span id='info-span'><b>Для выбора билета, введите фимилию и имя пассажира, "
                + "который будет осуществлять поездку</b>.</span>");

            $("#ts_chs_tbl").append("<button class='complex_btn' onclick='main.buy(" + train_number + ", " + dep_st_seq + ", " + arr_st_seq + ", " + price + ")'><b id='summa'>0 грн</b><span>Купить</span></button>");

            flag = false;
        } else if (selected.length == 0) {
            $("#info-span").remove();
            $(".complex_btn").remove();
            flag = true;
        }
        $("#summa").text(price * selected.length + "грн");
        console.log("selected: " + selected.toString() + ", length: " + selected.length)
    },


    close: function () {
        $(".vToolsPopupCoachScheme").remove();
        $("#background_div").hide(500);
        selected = [];
        flag = true;
        sum = 0;
    },

    buy: function (train_number, dep_st_seq, arr_st_seq, price) {
        //var strings = [];
        var seats = [];
        var dep_st = $("#dep_st").val();
        var arr_st = $("#arr_st").val();
        var email = $("#emailExit").val();

        for (var i = 0; i < selected.length; i++) {
            var strings = $("#chooseTR" + selected[i] + " > .place").text().split(' ');
            var carriage = parseInt(strings[1]);
            var seat = parseInt(strings[3]);
            var lastname = $("#chooseTR" + selected[i] + " .lastname").val();
            var name = $("#chooseTR" + selected[i] + " .firstname").val();
            seats.push({carriage: carriage, seat: seat, lastname: lastname, name: name});
        }

        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/APIHandlerServlet",
            data: {
                requestType: "buy",
                train_number: train_number,
                dep_date: departureDate,
                dep_st: dep_st,
                arr_st: arr_st,
                dep_st_seq: dep_st_seq,
                arr_st_seq: arr_st_seq,
                price: price,
                email: email,
                seats: seats
            },
            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - buy, data: ");
                    console.log(data);
                    alert("Спасибо за использование нашей онлайн кассой. Билет(ы) успешно куплен(ы)!")
                    main.close();

                } else if (data.errorCode == 10) {
                    alert("Извините, похоже, что кто-то уже купил один из выбраных Вами билетов!");
                } else if (data.errorCode == 11) {
                    alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                } else if (data.errorCode == 9) {
                    alert("Извините, но вас заблокировали!")
                } else {
                    alert("Неизвестный код ошибки, блок: success!")
                }
            },
            error: function (data) {
                alert("Ошибка покупки билета, блок: error!");
                console.log(data);
            }
        });
    },

    showUsers: function () {
        $.ajax({
                type: "GET",
                dataType: "json",
                url: "/APIHandlerServlet",
                data: {
                    requestType: "showUsers"
                },
                success: function (data) {
                    if (data.errorCode == undefined) {
                        console.log("Method - showUsers, data: ");
                        console.log(data);

                        $("#background_div").show();

                        $("body").append("<div class='vToolsPopupCoachScheme' style='z-index: 1004; position: absolute; "
                            + "top: 1055px; opacity: 1; width: 962px; visibility: visible; left: 183px; height: auto;"
                            + "background: #f1f2f4; border-radius: 3px; margin: 10px; box-shadow: 0 10px 15px 5px #6c6e6f;'></div>");


                        $(".vToolsPopupCoachScheme").append("<div class='vToolsPopupContent' style='overflow: auto; height: auto;'></div>");

                        $(".vToolsPopupContent").append("<div id='ts_chs_scheme' class='coach-scheme' style='min-height: 15px;'></div>");

                        $(".vToolsPopupContent").append("<div id='ts_chs_tbl' style='opacity: 1;'></div>");

                        $("#ts_chs_tbl").append("<table class='vToolsDataTableChoose' cellspacing='1' cellpadding='0' border='0' width='100%'></table>");

                        $(".vToolsDataTableChoose").append("<thead><tr>"
                            + "<th class='th1' style='width: 95px'>Number</th>"
                            + "<th class='th2' style='width: 95px'>ID</th>"
                            + "<th class='th3' style='width: 95px'>Email</th>"
                            + "<th class='th4' style='width: 95px'>Password</th>"
                            + "<th class='th5' style='width: 95px'>Name</th>"
                            + "<th class='th6' style='width: 95px'>Surname</th>"
                            + "<th class='th7' style='width: 95px'>isAdmin</th>"
                            + "<th class='th8' style='width: 95px'>isBlocked</th>"
                            + "<th class='th9' style='width: 95px'>Select</th>"
                            + "</tr></thead>");

                        $(".vToolsDataTableChoose").append("<tbody id='tbodyChoose'></tbody>");

                        for (var i = 0; i < Object.keys(data).length; i++) {

                            var result = data[i];
                            var y = (i % 2 == 0) ? '' : 2;

                            $("#tbodyChoose").append("<tr id='chooseTR" + i + "' class='vToolsDataTableRow" + y + "'></tr>");

                            $("#chooseTR" + i).append("<td class='num'>" + i + "</td>");

                            $("#chooseTR" + i).append("<td class='id' style='opacity: 1;'>" + result.id + "</td>");

                            $("#chooseTR" + i).append("<td class='email' style='opacity: 1;'>" + result.email + "</td>");

                            $("#chooseTR" + i).append("<td class='pass' style='opacity: 1;'>" + result.password + "</td>");

                            $("#chooseTR" + i).append("<td class='name' style='opacity: 1;'>" + result.name + "</td>");

                            $("#chooseTR" + i).append("<td class='surname' style='opacity: 1;'>" + result.surname + "</td>");

                            $("#chooseTR" + i).append("<td class='isAdmin' style='opacity: 1;'>" + result.isAdmin + "</td>");

                            $("#chooseTR" + i).append("<td class='isBlocked' style='opacity: 1;'>" + result.isBlocked + "</td>");

                            $("#chooseTR" + i).append("<td class='select' style='opacity: 1;'><input type='checkbox' id='checkbox" + i + "'></td>");

                            $("#chooseTR" + i).append("<br>");

                        }

                        $("#ts_chs_tbl").append("<br>")
                        $("#ts_chs_tbl").append("<p></p>")
                        $("#ts_chs_tbl p").append("<button type='button' class='btn btn-warning' style='margin: 20 20 20px;'  id='block' onclick='main.block()'><span>Block</span></button>");
                        $("#ts_chs_tbl p").append("<button type='button' class='btn btn-success' style='margin: 20 20 20px;'  id='unblock' onclick='main.unblock()'><span>Unblock</span></button>");
                        $("#ts_chs_tbl p").append("<button type='button' class='btn btn-danger'  style='margin: 20 20 20px;' id='delete' onclick='main.delete()'><span>Delete</span></button>");

                    } else if (data.errorCode == 11) {
                        alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                    } else if (data.errorCode == 9) {
                        alert("Извините, но вас заблокировали!")
                    } else {
                        alert("Ошибка, errorCode - не определен!");
                        console.log(data);
                    }
                },
                error: function (data) {
                    alert("Ошибка выбора, блок: error!");
                    console.log(data)
                }
            }
        );
    },
    block: function () {

        var checkboxes = $("input:checkbox:checked");
        var ids = [];

        for (var i = 0; i < checkboxes.length; i++) {
            var str = checkboxes[i].id;
            var num = str.substring(str.indexOf('x') + 1);
            ids.push($("#chooseTR" + num + " .id").text());
        }

        if (ids.length == 0) {
            alert("Чтобы заблокировать пользователей необходимо их выбрать!")
            return false;
        }

        $.ajax({
            type: "PUT",
            dataType: "json",
            url: "/APIHandlerServlet" + '?' + $.param({"requestType": "block", "ids": ids.toString()}),

            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - block, data: ");
                    console.log(data);
                    alert(data.rows + " пользовател(ь/я/ей) успешно заблокирован(ы)!")
                    main.close();
                    main.showUsers();
                } else if (data.errorCode == 11) {
                    alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                } else if (data.errorCode == 9) {
                    alert("Извините, но вас заблокировали!")
                } else {
                    alert("Неизвестный код ошибки, блок: success!")
                }
            },
            error: function (data) {
                alert("Ошибка блокировки пользователей, блок: error!");
                console.log(data);
            }
        });
    },

    unblock: function () {

        var checkboxes = $("input:checkbox:checked");
        var ids = [];

        for (var i = 0; i < checkboxes.length; i++) {
            var str = checkboxes[i].id;
            var num = str.substring(str.indexOf('x') + 1);
            ids.push($("#chooseTR" + num + " .id").text());
        }

        if (ids.length == 0) {
            alert("Чтобы разблокировать пользователей необходимо их выбрать!")
            return false;
        }

        $.ajax({
            type: "PUT",
            dataType: "json",
            url: "/APIHandlerServlet" + '?' + $.param({"requestType": "unblock", "ids": ids.toString()}),

            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - unblock, data: ");
                    console.log(data);
                    alert(data.rows + " пользовател(ь/я/ей) успешно разблокирован(ы)!")
                    main.close();
                    main.showUsers();
                } else if (data.errorCode == 11) {
                    alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                } else if (data.errorCode == 9) {
                    alert("Извините, но вас заблокировали!")
                } else {
                    alert("Неизвестный код ошибки, блок: success!")
                }
            },
            error: function (data) {
                alert("Ошибка разблокировки пользователей, блок: error!");
                console.log(data);
            }
        });
    },

    delete: function () {

        var checkboxes = $("input:checkbox:checked");
        var ids = [];

        for (var i = 0; i < checkboxes.length; i++) {
            var str = checkboxes[i].id;
            var num = str.substring(str.indexOf('x') + 1);
            ids.push($("#chooseTR" + num + " .id").text());
        }

        if (ids.length == 0) {
            alert("Чтобы удалить пользователей необходимо их выбрать!")
            return false;
        }

        $.ajax({
            type: "DELETE",
            dataType: "json",
            url: "/APIHandlerServlet" + '?' + $.param({"requestType": "delete", "ids": ids.toString()}),

            success: function (data) {
                if (data.errorCode == undefined) {
                    console.log("Method - delete, data: ");
                    console.log(data);
                    alert(data.rows + " пользовател(ь/я/ей) успешно удалены(ы)!")
                    main.close();
                    main.showUsers();
                } else if (data.errorCode == 11) {
                    alert("Ошибка аутентификации, возможно вы не вошли в систему!")
                } else if (data.errorCode == 9) {
                    alert("Извините, но вас заблокировали!")
                } else {
                    alert("Неизвестный код ошибки, блок: success!")
                }
            },
            error: function (data) {
                alert("Ошибка удаления пользователей, блок: error!");
                console.log(data);
            }
        });
    }
};