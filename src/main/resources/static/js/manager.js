let firstName = document.getElementById("nombre")
let lastName = document.getElementById("apellido")
let email = document.getElementById("email")
const app = {
    data(){
        return {
            database: [],
            json: null,
            firstName: null,
            lastName: null,
            email: null,
            emailRemove: null,
        }
    },
    created(){
        axios.get('/api/clients')
        .catch(error => console.log(error))
        .then(json => {
            console.log(json)
            this.database = json.data
            console.log(this.database)
            this.json = json;
        })
    },
    methods: {
        addClient() {
            axios.post('/api/clients', {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
            })
            location.reload()
        },
        removeClient(client){
            axios.delete(client._links.self.href)
            location.reload()
        }
    },
    computed:{

    }
}
Vue.createApp(app).mount("#app");
