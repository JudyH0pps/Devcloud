<script>
import http from "@/util/http-common"
import { Bar } from 'vue-chartjs'

export default {
    extends: Bar,
    data() {
        return {
            //loaded: false,
            chartData: {
                labels: [],
                datasets: [{
                    label: 'Tag about Questions',
                    borderWidth: 1,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)',
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    pointBorderColor: '#2554FF',

                    data: [

                    ],
                }]
            },
            options: {
                scales: {
                yAxes: [{
                    ticks: {
                    beginAtZero: true
                    },
                    gridLines: {
                    display: true
                    }
                }],
                xAxes: [{
                    gridLines: {
                    display: false
                    }
                }]
                },
                legend: {
                display: true
                },
                responsive: true,
                maintainAspectRatio: false
            }
        }
    },
    mounted() {
        //this.loaded = false
        this.getTagCnt();
        this.renderChart(this.chartData, this.options);
    },
    methods: {
        getTagCnt() {
            http
                .get('/api/tag/cnt')
                .then(({data}) => {
                    console.log(data);
                    var tag
                    for(tag = 0; tag <= 9; tag++){
                        this.chartData.labels.push(data[tag].name)
                        this.chartData.datasets[0].data.push(data[tag].cnt)
                    }
                })
                .catch(err => {
                    console.log(err);
                })
        }
    },
    created() {
        
    }
}
</script>