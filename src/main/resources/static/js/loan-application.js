const app = {
    data() {
        return {
            accounts: [],
            loans: [],
           /*  typeLoan: getForm("typeLoan"),
            payment: getForm("payment"),
            accDestiny: getForm("accDestiny"),
            amount: getForm("amount"), */
            typeLoan: "",
            payment: "",
            accDestiny: "",
            amount: "",        
        }
    },
    created() {

        axios.get('/api/clients/current')
            .catch(error => console.log(error))
            .then(json => {
                this.database = json.data
                this.accounts = json.data.accounts
                console.log(this.accounts)

            })
        axios.get('/api/loans')
            .catch(error => console.log(error))
            .then(json => {
                //console.log(json.data[0].accounts[0])
                this.loans = json.data
                console.log(this.loans)

            })
    },
    methods: {
        logOut() {
            axios.post('/api/logout').then(response => window.location.href = "/Web/index.html")
        },
        maxAmount(monto){
                let val = (monto / 1).toFixed(2).replace('.', ',')
                return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        createLoan() {
            if (this.typeLoan !== null && this.payment !== null && this.accDestiny !== null && this.amount !== null) {
                Swal.fire({
                    title: 'Esta seguro?',
                    text: "No podra revertir la solicitud",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Si, deseo solicitar el prestamo'
                })
                .then((result) =>  {
                    if (result.isConfirmed) {
                        axios.post('/api/loans', { id: this.typeLoan, amount: this.amount, payments: this.payment, accountDestiny: this.accDestiny })
                        .then(() => window.location.href="/Web/accounts.html")
                        .catch(error => console.log(error))
                        }
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
        },
    computed: {

    }
}
Vue.createApp(app).mount("#app");