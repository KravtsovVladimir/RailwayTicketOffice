/**
 * Created by Мир on 02.09.2016.
 */
$(function() {

    $("#registration-nav").validate({

        rules: {
            emailReg: {
                required: true,
                email: true
            },
            nameReg: {
                required: true,
                minlength: 2
            },
            surnameReg: {
                required: true,
                minlength: 2
            },
            passReg: {
                required: true,
                minlength: 4
            },
            passRegConf: {
                required: true,
                equalTo: "#passReg",
                minlength: 4
            },
        }

    });
    
    $("#fio_form").validate({
        
        rules: {
            lastname: {
                required: true,
                minlength: 2
            },
            firstname: {
                required: true,
                minlength: 2
            }
        }
    })

});