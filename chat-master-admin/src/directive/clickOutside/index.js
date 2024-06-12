/*
 * @Descripttion:  自定义指令，判断当前点击元素是本身还是元素外
 * @version: v1.0.0
 * @Author: yang
 * @Date: 2022-08-10 19:53:56
 * @LastEditors: yang
 * @LastEditTime: 2022-08-10 19:56:09
 */
export default{
    bind(el, binding) {
        function clickHandler(e) {
            if (el.contains(e.target)) {
                return false;
            }
            if (binding.expression) {
                binding.value(e);
            }
        }
        el.__vueClickOutside__ = clickHandler;
        document.addEventListener('click', clickHandler);
    },
    update() { },
    unbind(el) {
        // 解除事件监听
        document.removeEventListener('click', el.__vueClickOutside__);
        delete el.__vueClickOutside__;
    },
}