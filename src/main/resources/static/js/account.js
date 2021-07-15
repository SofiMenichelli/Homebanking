const app = {
    data() {
        return {
            database: [],
            transactions: [],
            account: [],
            isDebit:false,
        }
    },
    created() {
        const urlSearchParams = new URLSearchParams(window.location.search);
        const params = Object.fromEntries(urlSearchParams.entries());
        
        axios.get('/api/accounts/' + params.id)
            .catch(error => console.log(error))
            .then(json => {
                this.database = json.data
                this.account = json.data;
                this.transactions = json.data.transaction
                this.transactions.sort((a, b) => b.id - a.id)
            })
        axios.get('/api/clients/current', { headers: { 'accept': 'application/xml' } })
            .catch(err => console.log(err))
        },
        
        methods: {
            formateDate(date) {
                return date.split("").splice(2,5).join("")
            },
            logOut() {
                axios.post('/api/logout').then(response => window.location.href = "/Web/index.html")
            }
        },
        computed: {

        }
}
Vue.createApp(app).mount("#app");