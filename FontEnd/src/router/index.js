
import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '@/layout'

Vue.use(Router)
/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/login/index2'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  { path: '/403', component: () => import('@/views/403'), hidden: true },
  {
    path: '/',
    redirect: '/login'
    // component: () => import('@/views/404'),
    // hidden: true
  },

  {
    path: '/sso/:uid/:name/:role',
    component: () => import('@/views/sso'),
    hidden: true
  },
  {
    path: '/index',
    component: () => import('@/views/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/welcome',
    hidden: true,
    children: [
      {
        path: '/welcome',
        component: () => import('@/views/welcome')
      }
    ]
  },
  {
    path: '/incident',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/incident'),
    hidden: true
  },

  {
    path: '/farm',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/farm'),
    hidden: true
  },
  {
    path: '/pasture',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/pasture'),
    hidden: true
  },
  {
    path: '/shop',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/shop'),
    hidden: true
  },
  {
    path: '/mark',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/mark'),
    hidden: true
  },
  {
    path: '/batch_upload',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/batch_upload'),
    hidden: true
  },
  {
    path: '/check_batch',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/check_batch'),
    hidden: true
  },
  {
    path: '/irrigate',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/irrigate'),
    hidden: true
  },
  {
    path: '/fertilize',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/fertilize'),
    hidden: true
  },
  {
    path: '/feed',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/feed'),
    hidden: true
  },
  {
    path: '/poultry',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/poultry'),
    hidden: true
  },
  {
    path: '/poultryland',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/poultryland'),
    hidden: true
  },
  {
    path: '/crop',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/crop'),
    hidden: true
  },
  {
    path: '/cropland',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/cropland'),
    hidden: true
  },
  {
    path: '/user',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/user'),
    hidden: true
  },
  {
    path: '/area',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/area'),
    hidden: true
  },
  {
    path: '/market',
    // component: () => import('@/views/login/index2'),
    component: () => import('@/views/market'),
    hidden: true
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()
router.beforeEach((to, from, next) => {
  const role = window.localStorage.getItem('role')
  // 判断该路由是否需要登录权限
  if (to.meta && to.meta.roles) {
    if (role === 'admin' || to.meta.roles.includes(role)) {
      next()
    } else {
      next({path: '/403'})
    }
  } else {
    next()
  }
})

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter () {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
