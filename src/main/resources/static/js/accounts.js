let firstName = document.getElementById("nombre")
let lastName = document.getElementById("apellido")
let email = document.getElementById("email")
const app = {
    data() {
        return {
            database: [],
            account: [],
            json: null,
            firstName: null,
            lastName: null,
            email: null,
            emailRemove: null,
        }
    },
    created() {
        axios.get('/api/clients/1')
            .catch(error => console.log(error))
            .then(json => {
                this.database = json.data
                this.account = json.data.accounts
            })
    },
    methods: {

    },
    computed: {

    }
}
Vue.createApp(app).mount("#app");