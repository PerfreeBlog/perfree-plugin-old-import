function g(m) {
  return axios.post("/api/auth/plugin/oldImport/oldVersionImport", m);
}
const V = window.Vue.createTextVNode, s = window.Vue.resolveComponent, t = window.Vue.withCtx, a = window.Vue.createVNode, v = window.Vue.unref, b = window.Vue.openBlock, N = window.Vue.createElementBlock, P = { class: "page" }, I = window.Vue.reactive, n = window.Vue.ref, U = {
  __name: "OldImportView",
  setup(m) {
    let u = n(!1);
    const l = n({
      dataBaseIp: "",
      dataBasePort: "",
      dataBaseName: "",
      dataBaseUserName: "",
      dataBasePassword: ""
    }), f = I({
      dataBaseIp: [{ required: !0, message: "请输入老版本数据库IP", trigger: "blur" }],
      dataBasePort: [{ required: !0, message: "请输入老版本数据库端口", trigger: "blur" }],
      dataBaseName: [{ required: !0, message: "请输入老版本数据库名称", trigger: "blur" }],
      dataBaseUserName: [{ required: !0, message: "请输入老版本数据库账户", trigger: "blur" }],
      dataBasePassword: [{ required: !0, message: "请输入老版本数据库密码", trigger: "blur" }]
    }), p = n();
    function B() {
      p.value.validate((i) => {
        i && (u.value = !0, g(l.value).then((e) => {
          console.log(e), u.value = !1;
        }));
      });
    }
    return (i, e) => {
      const _ = s("el-alert"), d = s("el-form-item"), r = s("el-input"), w = s("el-button"), c = s("el-form");
      return b(), N("div", P, [
        a(_, {
          type: "warning",
          closable: !1
        }, {
          default: t(() => e[5] || (e[5] = [
            V(" 注: 只会导入分类、标签、文章、友链、附件、用户、评论等数据库数据，如数据已存在则会更新， 附件资源请手动将老版本根目录下resources/upload/attach目录中的内容复制到当前版本根目录resources/upload目录中, 以上步骤操作完毕后,如出现附件访问不到的问题,可在系统管理->附件存储策略中调整默认存储策略中的存储路径位置 ")
          ])),
          _: 1
        }),
        a(c, {
          ref_key: "addFormRef",
          ref: p,
          model: l.value,
          "label-width": "120px",
          "status-icon": "",
          rules: f
        }, {
          default: t(() => [
            a(d),
            a(d, {
              label: "数据库IP",
              prop: "dataBaseIp"
            }, {
              default: t(() => [
                a(r, {
                  modelValue: l.value.dataBaseIp,
                  "onUpdate:modelValue": e[0] || (e[0] = (o) => l.value.dataBaseIp = o),
                  placeholder: "请输入老版本数据库IP"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库端口",
              prop: "dataBasePort"
            }, {
              default: t(() => [
                a(r, {
                  modelValue: l.value.dataBasePort,
                  "onUpdate:modelValue": e[1] || (e[1] = (o) => l.value.dataBasePort = o),
                  placeholder: "请输入老版本数据库端口"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库名称",
              prop: "dataBaseName"
            }, {
              default: t(() => [
                a(r, {
                  modelValue: l.value.dataBaseName,
                  "onUpdate:modelValue": e[2] || (e[2] = (o) => l.value.dataBaseName = o),
                  placeholder: "请输入老版本数据库名称"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库账户",
              prop: "dataBaseUserName"
            }, {
              default: t(() => [
                a(r, {
                  modelValue: l.value.dataBaseUserName,
                  "onUpdate:modelValue": e[3] || (e[3] = (o) => l.value.dataBaseUserName = o),
                  placeholder: "请输入老版本数据库账户"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库密码",
              prop: "dataBasePassword"
            }, {
              default: t(() => [
                a(r, {
                  modelValue: l.value.dataBasePassword,
                  "onUpdate:modelValue": e[4] || (e[4] = (o) => l.value.dataBasePassword = o),
                  placeholder: "请输入老版本数据库密码"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, null, {
              default: t(() => [
                a(w, {
                  type: "primary",
                  onClick: B,
                  loading: v(u)
                }, {
                  default: t(() => e[6] || (e[6] = [
                    V("开始导入")
                  ])),
                  _: 1
                }, 8, ["loading"])
              ]),
              _: 1
            })
          ]),
          _: 1
        }, 8, ["model", "rules"])
      ]);
    };
  }
};
export {
  U as default
};
