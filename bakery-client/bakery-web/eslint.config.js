import pluginJs from '@eslint/js';
import astro from 'eslint-plugin-astro';
import jsdoc from 'eslint-plugin-jsdoc';

/** @type {import('eslint').Linter.Config[]} */
export default [
  pluginJs.configs.recommended,
  ...astro.configs.recommended,
  jsdoc.configs['flat/recommended'],
];

