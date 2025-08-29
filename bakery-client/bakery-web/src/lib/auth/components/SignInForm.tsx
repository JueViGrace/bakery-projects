import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@components/ui/form';
import { toast } from 'sonner';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button } from '@components/ui/button';
import { Input } from '@components/ui/input';
import {
  type SignInRequest,
  type SignInFormSchema,
  signInFormSchema,
} from '@auth/types';
import { navigate } from 'astro:transitions/client';
import type { ActionError } from 'astro:actions';

export default function SignInForm() {
  const form = useForm<SignInFormSchema>({
    resolver: zodResolver(signInFormSchema),
    defaultValues: {
      username: '',
      password: '',
    },
  });

  const onSubmit = async ({ username, password }: SignInFormSchema) => {
    const signInDto: SignInRequest = {
      username: username,
      password: password,
    };
    try {
      const req = await fetch('/api/auth/signin', {
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(signInDto),
        method: 'POST',
      });

      if (!req.ok) {
        const res: ActionError = await req.json();
        toast.error(res.message);
        return;
      }

      toast.success('Welcome!');
      navigate('/');
    } catch (e) {
      console.error('Sign in form submission error', e);
      toast.error('Failed to submit the form. Please try again.');
    }
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex flex-col items-center justify-center gap-6"
      >
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem className="w-full">
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input
                  placeholder="Username or email..."
                  type="text"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <div className="flex w-full flex-col items-center justify-center gap-1">
              <FormItem className="w-full">
                <FormLabel>Password</FormLabel>
                <FormControl>
                  <Input type="password" placeholder="Password..." {...field} />
                </FormControl>
                <FormMessage />
                <a
                  className="ml-auto cursor-pointer text-sm underline"
                  href="/auth/forgot/password"
                >
                  Forgot password?
                </a>
              </FormItem>
            </div>
          )}
        />

        <Button type="submit" className="w-full cursor-pointer">
          Submit
        </Button>
      </form>
    </Form>
  );
}
