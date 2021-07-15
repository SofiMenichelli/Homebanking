const app = {
    data() {
        return {
            name: null,
            maxAmount: null,
            payment: null,
            fee: null,
            
        }
    },
    created() {

    },
    methods: {

        createLoan() {
            axios.post('/api/loan',  "name=" + this.name + "&maxAmount=" + this.maxAmount + "&payments=" + this.payment + "&fee=" + this.fee, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() => console.log('created'))
                .then(() => window.location.href = "/Web/index.html")
                .catch(error => console.log(error))
        },
    },
    computed: {

    }
}
Vue.createApp(app).mount("#app");