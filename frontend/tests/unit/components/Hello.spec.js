import { shallowMount, createLocalVue } from '@vue/test-utils';
import Vuex from 'vuex';
import Hello from '@/components/Hello'

const store = new Vuex.Store({});

describe('Hello.vue', () => {
  it('should render correct hello message', () => {
    // Given
    const hellowrapped = shallowMount(Hello, {
      store: store,
      propsData: { hellomsg: 'Welcome to your Jest powered Vue.js App' },
      stubs: ['router-link', 'router-view']
    });

    // When
    const contentH1 = hellowrapped.find('h1');

    // Then
    expect(contentH1.text()).toEqual('Welcome to your Jest powered Vue.js App');
  })
})
