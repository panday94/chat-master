import Vue from 'vue'
import DataDict from '@/utils/dict'
import { getDicts as getDicts } from '@/api/sys/common'

function install() {
  Vue.use(DataDict, {
    metas: {
      '*': {
        labelField: 'dictLabel',
        valueField: 'dictValue',
        request(dictMeta) {
          return getDicts(dictMeta.type).then((res) => {
            return res.data.map(e => {
              if (e.value == '0' || e.value > 0 || e.value < 0) {
                return {
                  ...e,
                  value: Number(e.value)
                }
              }
              else {
                return e
              }
            })
          })
        },
      },
    },
  })
}

export default {
  install,
}