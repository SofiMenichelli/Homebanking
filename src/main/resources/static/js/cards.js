
const app = {
    data() {
        return {
            cards: [],
            cardsCredit: [],
            cardsDebit: [],
            modalShow: false,
            typeCard: "",
            typeColor: "",
            selected: "",
        }
    },
    created() {
        axios.get('http://localhost:8080/api/clients/current', { headers: { 'accept': 'application/xml' } })
            .catch(err => console.log(err))
        axios.get('/api/clients/current')
            .catch(error => console.log(error))
            .then(json => {
                this.cards = json.data.cards
                this.cardsCredit = this.cards.filter(card => card.cardType == "CREDIT")
                this.cardsDebit = this.cards.filter(card => card.cardType == "DEBIT")
                console.log(this.cards)
                console.log(this.cardsCredit, this.cardsDebit)

            })
    },
    methods: {
        logOut() {
            axios.post('/api/logout').then(response => window.location.href = "/Web/index.html")
        },
        cortarFecha(fecha) {
            return fecha.split("").splice(2, 5).join("")
        },
        redirect(){
            this.modalShow = !this.modalShow
        },
        estaVencida(thruDate) {
            //let hoy = new Date(Date.now()).toLocaleString().split(',')[0];
            let hoy = new Date().toJSON().slice(0, 10).split('-').join('-');
            if(thruDate.valueOf() < hoy.valueOf()) {
                console.log(hoy, thruDate);
                return true
            }
        },
        deleteCard(id){
            Swal.fire({
                title: 'Esta seguro?',
                text: "No podra revertir este cambio",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, deseo borrarla'})
                .then((result) => {
                    if (result.isConfirmed) {
                    axios.post('/api/cards', "id=" + id, { headers: { 'content-type': 'application/x-www-form-urlencoded' } }) 
                    window.location.reload()
                    Swal.fire(
                        'Borrado',
                        'Su tarjeta ha sido eliminada',
                        'success'
                    )
                }
            })
           .catch(err => console.log(err));
        },
        addCard() {
            axios.post('/api/clients/current/cards', "color=" + this.typeColor.toUpperCase() + "&type=" + this.typeCard.toUpperCase(), { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(window.location.href = "/Web/cards.html")
        },
    },
    computed: {

    }
}
Vue.createApp(app).mount("#app");
