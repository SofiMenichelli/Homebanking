
const app = {
    data() {
        return {
            accounts: [],
            loans: [],
            modalShow: false,
            AccountType: "",
        }
    },
    created() {
        axios.get('/api/clients/current', { headers: { 'accept': 'application/xml' } })
            .catch(err => console.log(err))

        axios.get('/api/clients/current')
            .then(json => {
                this.accounts = json.data.accounts
                this.loans = json.data.loans
            })
    },
    methods: {
        logOut() {
            axios.post('/api/logout').then(response => window.location.href = "/Web/index.html")
        },
        createAccount(){
            this.modalShow = !this.modalShow;
            Swal.fire({
                title: 'Esta de que desea obtener una cuenta?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, deseo crear la cuenta'
            })
            .then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/accounts', "accountType=" + this.AccountType, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .catch(err => swal("Usted no puede crear mas cuentas"))
                    Swal.fire(
                        'Creada',
                        'Que disfrute su nueva cuenta',
                        'success'
                    )
                }
                setTimeout(function () { window.location.reload(); }, 2000)
            })

            .catch(err => console.log(err))
        },
        createLoan(){
            window.location.href = '/Web/loan-application.html'
        },
        deleteAccount(id) {
            Swal.fire({
                title: 'Esta seguro?',
                text: "No podra revertir este cambio",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, deseo borrar la cuenta'
            })
                .then((result) => {
                    if (result.isConfirmed) {
                        axios.patch('/api/accounts', "id=" + id, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        window.location.reload()
                        Swal.fire(
                            'Borrada',
                            'Su Cuenta ha sido eliminada',
                            'success'
                        )
                    }
                })
                .catch(err => console.log(err));
        }
    },
    computed: {
        
    }
}
Vue.createApp(app).mount("#app");
