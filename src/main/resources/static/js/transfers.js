const app = {
    data() {
        return {
            database: [],
            accDesc: "",
            accOrigin: "",
            accDestino: "",
            accMonto: "",
            selected: "",
        }
    },
    created() {
        axios.get('/api/clients/current')
            .catch(error => console.log(error))
            .then(json => {
                this.database = json.data.accounts
               
            })
        axios.get('/api/clients/current', { headers: { 'accept': 'application/xml' } })
            .catch(err => console.log(err))
    },
    methods: {
        logOut() {
            axios.post('/api/logout').then(response => window.location.href = "/Web/index.html")
        },
        transfer(){
            Swal.fire({
                title: 'Esta seguro que desea transferir?',
                text: "La transacción no podra ser revertida",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
            })
            .then((result) =>
            {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/accounts/transactions', "amount=" + this.accMonto + "&description="+ this.accDesc + "&accOrigin=" + this.accOrigin + "&accDestiny=" + this.accDestino, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    window.location.href = "/Web/accounts.html"
                    Swal.fire(
                        'Realizada',
                        'Su transaccion sera impactada en las proximas 48hs',
                        'success'
                    )
                }
            })
             .catch(error => swal("El monto excede su saldo"))
        }
    },
    computed: {

    }
}
Vue.createApp(app).mount("#app");