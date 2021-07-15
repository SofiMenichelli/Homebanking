const app = {
    data() {
        return {
            email: "",
            psw: "",
            firstName: "",
            lastName: "",
            signIn: false,
            register: true,
        }
    },
    created() {
        axios.get('/api/clients', { headers: { 'accept': 'application/xml' } })
            .catch(err => console.log(err))
    },
    methods: {
        reset(){
            this.email = null,
            this.psw = null,
            this.firstName = null,
            this.lastName = null
        },
        login(){
            if (this.email != null && this.psw != null) {
                axios.post('/api/login', "email=" + this.email + "&password=" + this.psw, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response =>  window.location.href = "/Web/accounts.html")
                .catch(error => {
                    this.psw = null
                    this.email = null
                    Swal.fire({
                    title: 'Usuario o contraseña incorrectos',
                    showConfirmButton: false,
                    icon: "error",
                    showCloseButton: true,
                })
            })
            } else {
                Swal.fire({
                    title: 'Por favor complete todos los campos',
                    showConfirmButton: false,
                    icon: "error",
                    showCloseButton: true,
                })
            }
        },
        registro(){
            if(this.email != null && this.psw != null && this.firstName != null && this.lastName != null){
                let contieneArroba = this.email.includes('@' && ".");
                if(contieneArroba) {
                    axios.post('/api/clients', "firstName=" + this.firstName + "&lastName=" + this.lastName + "&email=" + this.email + "&password=" + this.psw, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(response => {
                            axios.post('/api/clients/current/accounts', { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                            axios.post('/api/login', "email=" + this.email + "&password=" + this.psw, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                            console.log("chau")
                            Swal.fire({
                                title: 'Se ha registrado con exito.',
                                icon: "success",
                            })
                            setTimeout(function () { window.location.href = "/Web/accounts.html" }, 2000)
                        })
                        .catch(error => {
                            this.reset();
                         console.log(error)
                        })
                } else {
                    Swal.fire({
                        title: 'Por favor ingrese un mail válido',
                        showConfirmButton: false,
                        icon: "error",
                        showCloseButton: true,
                    })
                } 
                
            } else {
            Swal.fire({
                title: 'Por favor ingrese todos los campos',
                showConfirmButton: false,
                icon: "error",
                showCloseButton: true,
            })
            }
            },  
        logOut(){
            axios.post('/api/logout').then(response => window.location.href = "/Web/index.html")
        },
    },
    computed: {
    }
}
Vue.createApp(app).mount("#app");

/* BOTONES Inicio / registro */

const loginBtn = document.getElementById('login');
const signupBtn = document.getElementById('signup');
loginBtn.addEventListener('click', (e) => {
    let parent = e.target.parentNode.parentNode;
    Array.from(e.target.parentNode.parentNode.classList).find((element) => {
        if (element !== "slide-up") {
            parent.classList.add('slide-up')
        } else {
            signupBtn.parentNode.classList.add('slide-up')
            parent.classList.remove('slide-up')
        }
    });
});
signupBtn.addEventListener('click', (e) => {
    let parent = e.target.parentNode;
    Array.from(e.target.parentNode.classList).find((element) => {
        if (element !== "slide-up") {
            parent.classList.add('slide-up')
        } else {
            loginBtn.parentNode.parentNode.classList.add('slide-up')
            parent.classList.remove('slide-up')
        }
    });
});

