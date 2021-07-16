const app = {
    data() {
        return {
            number: "",
            cvv: "",
            amount: "",
            desc: "",
        }
    },
    created() {
        axios.get('api/cards/create')
        .then(res => console.log(res.json()))
    },
    methods: {
        createLoan() {
            if (this.number !== null && this.cvv !== null && this.amount !== null && this.desc !== null) {
                Swal.fire({
                    title: 'Esta seguro?',
                    text: "No podra revertir la solicitud",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Si, deseo hacer el pago'
                })
                    .then((result) => {
                        if (result.isConfirmed) {
                            axios.post('/api/loans', { number: this.number, cvv: this.cvv, amount: this.amount, description: this.desc })
                                .then(() => window.location.href = "/Web/accounts.html")
                                .catch(error => console.log(error))
                        }
                    })
                    .catch((error) => alert(error.response.data))
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