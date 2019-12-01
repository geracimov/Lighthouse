var options = {
    url: 'http://localhost:10000/api',
    method: 'GET',
    headers:
        {
            'Authorization': 'Basic aG9tZTpob21lUGFzcw=='
        }
};

Vue.component('component-row', {
    props: ['componentData'],
    template: '<div><i>{{componentData.name}}</i> > <span>{{componentData.instanceCount}}</span></div>'
});

Vue.component('component-list', {
    props: ['components'],
    template: '<div>' +
        '<h4>Components</h4>' +
        '<component-row v-for="component in components" :key="component.name" :componentData="component"/>' +
        '</div>',
    created: function () {
        this.$http(options).then(res => res.json().then(
            data =>
                data.forEach(component => this.components.push(component))
        ))
    }
});

var app = new Vue({
    el: '#app',
    template: '<component-list :components="components"/>',
    data: {
        components: []
    }
});